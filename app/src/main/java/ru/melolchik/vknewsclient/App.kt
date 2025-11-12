package ru.melolchik.vknewsclient

import android.app.Application
import com.vk.id.VKID
import ru.melolchik.vknewsclient.di.ApplicationComponent
import ru.melolchik.vknewsclient.di.DaggerApplicationComponent

class App : Application() {

    val component: ApplicationComponent by lazy {

        DaggerApplicationComponent.factory().create(
            this
        )
    }

    override fun onCreate() {
        super.onCreate()
        VKID.init(this)


    }
}