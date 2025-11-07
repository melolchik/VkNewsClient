package ru.melolchik.vknewsclient.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepository
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment

class CommentsViewModel(val feedPost: FeedPost) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    val screenState : LiveData<CommentsScreenState> = _screenState

    val repository = NewsFeedRepository()

    init{
        loadComments(feedPost)
    }
    private fun loadComments(feedPost: FeedPost){

        viewModelScope.launch {
            val comments = repository.getComments(feedPost = feedPost)
            _screenState.value = CommentsScreenState.Comments(feedPost, comments)
        }

    }

}