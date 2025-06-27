package ru.melolchik.vknewsclient.ui.theme

import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()
    data class Posts(val posts : List<FeedPost>) : HomeScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : HomeScreenState()
}