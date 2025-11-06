package ru.melolchik.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepository
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val repository = NewsFeedRepository()

    private val initState = NewsFeedScreenState.Initial
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val posts = repository.loadData()
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }

    fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadData()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            if (feedPost.isLiked) {
                repository.deleteLike(feedPost = feedPost)
            } else {
                repository.addLike(feedPost = feedPost)
            }
            _screenState.postValue(NewsFeedScreenState.Posts(posts = repository.feedPosts))
        }
    }




    private fun List<FeedPost>.getItemById(id: Long): FeedPost {
        return this.find { it.id == id }
            ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) {
            return
        }
        viewModelScope.launch {
            repository.deletePost(feedPost = feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }
    }


}