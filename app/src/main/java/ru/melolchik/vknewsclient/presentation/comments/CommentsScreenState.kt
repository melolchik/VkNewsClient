package ru.melolchik.vknewsclient.presentation.comments

import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : CommentsScreenState()
}