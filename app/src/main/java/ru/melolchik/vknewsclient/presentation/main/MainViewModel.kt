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
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val _authSate  = MutableLiveData<AuthState>(AuthState.Initial)

    val authState : LiveData<AuthState> = _authSate

    init {
        _authSate.value = if(VKID.instance.accessToken != null) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun login(){
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    _authSate.postValue(AuthState.Authorized)
                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    when (fail) {
                        is VKIDAuthFail.Canceled -> { /*...*/

                        }

                        else -> {
                            //...
                        }
                    }
                }
            }

            VKID.instance.authorize(vkAuthCallback)
        }
    }

}