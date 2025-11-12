package ru.melolchik.vknewsclient

import android.app.Application
import com.vk.id.VKID
import ru.melolchik.vknewsclient.di.ApplicationComponent

class App : Application() {

    val component: ApplicationComponent by lazy {

TODO()
    }

    override fun onCreate() {
        super.onCreate()
        VKID.init(this)


    }
}