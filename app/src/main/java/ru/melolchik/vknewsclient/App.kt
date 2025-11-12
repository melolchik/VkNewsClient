package ru.melolchik.vknewsclient

import android.app.Application
import com.vk.id.VKID
import ru.melolchik.vknewsclient.di.ApplicationComponent
import ru.melolchik.vknewsclient.di.DaggerApplicationComponent
import ru.melolchik.vknewsclient.domain.entity.FeedPost

class App : Application() {

    val component: ApplicationComponent by lazy {

        DaggerApplicationComponent.factory().create(
            this, FeedPost(
                id = 0,
                communityId = 0,
                communityName = "",
                publicationDate = "",
                communityImageUrl = "",
                contentText = "",
                contentImageUrl = "",
                statistics = emptyList(),
                isLiked = true
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        VKID.init(this)


    }
}