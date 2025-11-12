package ru.melolchik.vknewsclient.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.usecases.ChangeLikeStatusUseCase
import ru.melolchik.vknewsclient.domain.usecases.DeletePostUseCase
import ru.melolchik.vknewsclient.domain.usecases.GetFeedPostsListUseCase
import ru.melolchik.vknewsclient.domain.usecases.LoadNextDataUseCase
import ru.melolchik.vknewsclient.extension.mergeWith
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getFeedPostsListUseCase: GetFeedPostsListUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {


    val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        ///log

    }

    val repositoryDataFlow = getFeedPostsListUseCase()

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
            loadNextDataUseCase()
        }

    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost = feedPost)
        }
    }


    private fun List<FeedPost>.getItemById(id: Long): FeedPost {
        return this.find { it.id == id }
            ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost) {

        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost = feedPost)
        }
    }


}