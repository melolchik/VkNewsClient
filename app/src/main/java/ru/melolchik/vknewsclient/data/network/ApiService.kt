package ru.melolchik.vknewsclient.data.network

import ru.melolchik.vknewsclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}
