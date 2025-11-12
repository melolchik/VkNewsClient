package ru.melolchik.vknewsclient

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun getApplicationComponent() : ApplicationComponent{
    return (LocalContext.current.applicationContext as App).component
}