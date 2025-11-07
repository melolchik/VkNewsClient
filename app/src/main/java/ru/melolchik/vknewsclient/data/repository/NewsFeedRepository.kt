package ru.melolchik.vknewsclient.data.repository

import com.vk.id.VKID
import ru.melolchik.vknewsclient.data.mapper.NewsFeedMapper
import ru.melolchik.vknewsclient.data.network.ApiFactory
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.domain.StatisticType

class NewsFeedRepository {

    private val accessToken = VKID.instance.accessToken

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()

    private var nextFrom: String? = null;

    private val _feedPosts = mutableListOf<FeedPost>()

    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()


    suspend fun loadData(): List<FeedPost> {
        val startFrom = nextFrom
        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts
        val response = if (startFrom == null) {
            apiService.loadNewsfeed(getAccessToken())
        } else {
            apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun deletePost(feedPost: FeedPost)  {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToComments(comments)
    }

    private fun getAccessToken(): String {
        return accessToken?.token ?: throw IllegalStateException("Token is null")
    }

    suspend fun addLike(feedPost: FeedPost) {
        val response = apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = true)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

    suspend fun deleteLike(feedPost: FeedPost) {
        val response = apiService.deleteLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = false)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }


}