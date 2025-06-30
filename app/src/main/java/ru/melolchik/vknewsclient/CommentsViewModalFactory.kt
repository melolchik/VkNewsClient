package ru.melolchik.vknewsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.melolchik.vknewsclient.domain.FeedPost

class CommentsViewModalFactory(private val feedPost: FeedPost) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}