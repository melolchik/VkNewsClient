package ru.melolchik.vknewsclient.data.network

import ru.melolchik.vknewsclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import ru.melolchik.vknewsclient.data.model.LikesCountResponseDto

interface ApiService {

    @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto


    @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String,

    ): NewsFeedResponseDto

    @GET("newsfeed.ignoreItem?type=wall&v=5.199")
    suspend fun ignoreItem(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
        )

    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ) : LikesCountResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ) : LikesCountResponseDto
}
