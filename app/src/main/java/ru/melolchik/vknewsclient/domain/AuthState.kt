package ru.melolchik.vknewsclient.domain

sealed class AuthState {
    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()
}