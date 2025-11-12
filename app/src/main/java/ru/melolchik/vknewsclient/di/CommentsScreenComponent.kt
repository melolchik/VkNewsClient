package ru.melolchik.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.presentation.ViewModelFactory

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getViewModelFactory() : ViewModelFactory

    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}