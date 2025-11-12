package ru.melolchik.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.melolchik.vknewsclient.domain.entity.AuthState
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.entity.PostComment
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import java.lang.AutoCloseable
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(val repository: NewsFeedRepository) {
    operator fun invoke(): Flow<AuthState> {
        return repository.getAuthStateFlow()
    }
}