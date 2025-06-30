package ru.melolchik.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.ui.theme.CommentsScreenState
import ru.melolchik.vknewsclient.ui.theme.NewsFeedScreenState

class CommentsViewModel : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    val screenState : LiveData<CommentsScreenState> = _screenState

    init{
        loadComments(FeedPost(0))
    }
    fun loadComments(feedPost: FeedPost){
        val comments = mutableListOf<PostComment>().apply {
            repeat(20){
                add(PostComment(it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }

}