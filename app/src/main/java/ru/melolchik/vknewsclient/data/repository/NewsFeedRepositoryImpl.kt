package ru.melolchik.vknewsclient.data.repository

import com.vk.id.AccessToken
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.melolchik.vknewsclient.data.mapper.NewsFeedMapper
import ru.melolchik.vknewsclient.data.network.ApiFactory
import ru.melolchik.vknewsclient.data.network.ApiService
import ru.melolchik.vknewsclient.domain.entity.FeedPost
import ru.melolchik.vknewsclient.domain.entity.PostComment
import ru.melolchik.vknewsclient.domain.entity.StatisticItem
import ru.melolchik.vknewsclient.domain.entity.StatisticType
import ru.melolchik.vknewsclient.extension.mergeWith
import ru.melolchik.vknewsclient.domain.entity.AuthState
import ru.melolchik.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class  NewsFeedRepositoryImpl @Inject constructor(
    private val apiService : ApiService,
    private val mapper : NewsFeedMapper,
    private val accessToken : AccessToken?

) : NewsFeedRepository{

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val refreshDataEvents = MutableSharedFlow<List<FeedPost>>()


    private var nextFrom: String? = null;

    private val _feedPosts = mutableListOf<FeedPost>()

    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private val checkAuthStateFlow = MutableSharedFlow<Unit>( replay = 1)


    private val authStateFlow = flow {
        checkAuthStateFlow.emit(Unit)
        checkAuthStateFlow.collect {
            val authState = if(VKID.instance.accessToken != null) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }

    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
    )

    override suspend fun checkAuthState(){
        checkAuthStateFlow.emit(Unit)
    }
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadNewsfeed(getAccessToken())
            } else {
                apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
        .retry(2) {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }.catch {

        }


    private val data: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshDataEvents)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataEvents.emit(feedPosts)

    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        if(feedPost.isLiked){
            deleteLike(feedPost = feedPost)
        }else{
            addLike(feedPost = feedPost)
        }
    }

    override fun getAuthStateFlow(): StateFlow<AuthState>  = authStateFlow as StateFlow<AuthState>

    override fun getFeedPostsListFlow(): StateFlow<List<FeedPost>>  = data

    override fun getCommentsFlow(feedPost: FeedPost): Flow<List<PostComment>> = flow {
            val comments = apiService.getComments(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
            emit(mapper.mapResponseToComments(comments))
        }.retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

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
        refreshDataEvents.emit(feedPosts)
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
        refreshDataEvents.emit(feedPosts)
    }


    companion object {
        const val RETRY_TIMEOUT_MILLIS = 3000L
    }

}