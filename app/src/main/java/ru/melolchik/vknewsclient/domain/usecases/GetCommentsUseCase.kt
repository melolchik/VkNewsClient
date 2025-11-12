package ru.melolchik.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.entity.PostComment
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(val repository: NewsFeedRepository) {
    operator fun invoke(feedPost: FeedPost): Flow<List<PostComment>> {
        return repository.getCommentsFlow(feedPost = feedPost)
    }
}