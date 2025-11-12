package ru.melolchik.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
        repository.loadNextData()
    }
}