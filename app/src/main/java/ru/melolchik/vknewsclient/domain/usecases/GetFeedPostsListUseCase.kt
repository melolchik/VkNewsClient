package ru.melolchik.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetFeedPostsListUseCase @Inject constructor(val repository: NewsFeedRepository) {
    operator fun invoke() : StateFlow<List<FeedPost>> {
        return repository.getFeedPostsListFlow()
    }
}