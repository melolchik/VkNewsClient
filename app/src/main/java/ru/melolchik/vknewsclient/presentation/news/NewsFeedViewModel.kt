package ru.melolchik.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.id.VKID
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.data.mapper.NewsFeedMapper
import ru.melolchik.vknewsclient.data.network.ApiFactory
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val mapper = NewsFeedMapper()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val token = VKID.instance.accessToken?.token ?: return@launch
            val response = ApiFactory.apiService.loadNewsfeed(token)
            val posts = mapper.mapResponseToPosts(response)
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }


    private val initState = NewsFeedScreenState.Initial
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState


    private fun List<FeedPost>.getItemById(id: String): FeedPost {
        return this.find { it.id == id }
            ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) {
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(oldPosts)
    }

    public fun updateStatistics(feedPost: FeedPost, statisticItem: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) {
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        val feedPostItem = oldPosts.getItemById(feedPost.id)
        val oldStatistics = feedPostItem.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == statisticItem.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        //val newFeedPostItem = feedPostItem.copy(statistics = newStatistics)
        val newFeedPostList = oldPosts.apply {
            replaceAll { oldItem ->
                if (oldItem.id == feedPost.id) {
                    oldItem.copy(statistics = newStatistics)
                } else {
                    oldItem
                }

            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newFeedPostList)

    }
}