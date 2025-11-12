package ru.melolchik.vknewsclient.domain.usecases

import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(val repository: NewsFeedRepository) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.deletePost(feedPost = feedPost)
    }
}