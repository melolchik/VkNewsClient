package ru.melolchik.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.melolchik.vknewsclient.domain.entity.AuthState
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.entity.PostComment

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getFeedPostsListFlow(): StateFlow<List<FeedPost>>

    fun getCommentsFlow(feedPost: FeedPost): Flow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)


}