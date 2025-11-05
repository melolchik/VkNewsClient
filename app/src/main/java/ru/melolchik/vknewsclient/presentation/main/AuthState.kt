package ru.melolchik.vknewsclient.presentation.main

sealed class AuthState {
    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()
}