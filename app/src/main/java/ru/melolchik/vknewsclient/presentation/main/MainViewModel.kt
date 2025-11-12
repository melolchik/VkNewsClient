package ru.melolchik.vknewsclient.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.domain.usecases.CheckAuthStateUseCase
import ru.melolchik.vknewsclient.domain.usecases.GetAuthStateFlowUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateFlowUseCase()

    fun login() {
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    viewModelScope.launch {
                        checkAuthStateUseCase()
                    }

                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    viewModelScope.launch {
                        checkAuthStateUseCase()
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
                scopes = setOf("wall", "friends")

            }.build()

            VKID.instance.authorize(
                callback = vkAuthCallback,
                params = initializer
            )
        }
    }

}