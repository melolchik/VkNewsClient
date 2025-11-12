package ru.melolchik.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.usecases.GetCommentsUseCase
import ru.melolchik.vknewsclient.domain.usecases.GetFeedPostsListUseCase
import ru.melolchik.vknewsclient.domain.usecases.LoadNextDataUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val state = getCommentsUseCase(feedPost = feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }
        .onStart {
            emit(CommentsScreenState.Initial)
        }


}