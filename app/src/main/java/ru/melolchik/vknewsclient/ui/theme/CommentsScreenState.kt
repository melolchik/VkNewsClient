package ru.melolchik.vknewsclient.ui.theme

import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : CommentsScreenState()
}