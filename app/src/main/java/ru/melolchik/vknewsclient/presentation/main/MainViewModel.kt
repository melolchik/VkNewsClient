package ru.melolchik.vknewsclient.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.auth.VKIDAuthUiParams
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepository

class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val repository = NewsFeedRepository()

    val authState = repository.authStateFlow

   fun login(){
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    viewModelScope.launch {
                        repository.checkAuthState()
                    }

                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    viewModelScope.launch {
                        repository.checkAuthState()
                    }
                    when (fail) {
                        is VKIDAuthFail.Canceled -> { /*...*/

                        }

                        else -> {
                            //...
                        }
                    }
                }
            }

            val initializer = VKIDAuthParams.Builder().apply {
                scopes = setOf("wall","friends")

            }.build()

            VKID.instance.authorize(callback =  vkAuthCallback,
                params = initializer)
        }
    }

}