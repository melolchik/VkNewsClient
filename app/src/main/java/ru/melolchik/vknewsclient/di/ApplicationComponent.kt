package ru.melolchik.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.presentation.main.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getCommentsScreenComponentFactory() : CommentsScreenComponent.Factory

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}