package ru.melolchik.vknewsclient.domain.entity

sealed class AuthState {
    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()
}