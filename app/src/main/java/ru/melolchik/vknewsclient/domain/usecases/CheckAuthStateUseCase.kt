package ru.melolchik.vknewsclient.domain.usecases

import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
        return repository.checkAuthState()
    }
}