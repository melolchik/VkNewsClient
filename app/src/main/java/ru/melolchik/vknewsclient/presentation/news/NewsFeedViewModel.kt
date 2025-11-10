package ru.melolchik.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepository
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.extension.mergeWith

class NewsFeedViewModel : ViewModel() {

    private val repository = NewsFeedRepository()

    val repositoryDataFlow = repository.data

    val loadingState = MutableSharedFlow<NewsFeedScreenState>()
    val screenState = repositoryDataFlow
        .filter { posts -> posts.isNotEmpty() }
        .map {
            NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState
        }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadingState)



    fun loadNextData() {

        viewModelScope.launch {
            loadingState.emit(
                NewsFeedScreenState.Posts(
                    posts = repositoryDataFlow.value,
                    nextDataIsLoading = true
                )
            )
            repository.loadNextData()
        }

    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            if (feedPost.isLiked) {
                repository.deleteLike(feedPost = feedPost)
            } else {
                repository.addLike(feedPost = feedPost)
            }
        }
    }


    private fun List<FeedPost>.getItemById(id: Long): FeedPost {
        return this.find { it.id == id }
            ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost) {

        viewModelScope.launch {
            repository.deletePost(feedPost = feedPost)
        }
    }


}