package ru.melolchik.vknewsclient.di

import com.vk.id.AccessToken
import com.vk.id.VKID
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.melolchik.vknewsclient.data.network.ApiFactory
import ru.melolchik.vknewsclient.data.network.ApiService
import ru.melolchik.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository


@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object{
        @ApplicationScope
        @Provides
        fun providesApiService() : ApiService{
            return ApiFactory.apiService
        }
        @ApplicationScope
        @Provides
        fun vkAccessToken() : AccessToken? {
            return VKID.instance.accessToken
        }
    }

}