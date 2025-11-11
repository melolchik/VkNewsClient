package ru.melolchik.vknewsclient.domain.usecases

import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
        return repository.checkAuthState()
    }
}