package ru.melolchik.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.ui.theme.NewsFeedScreenState

class NewsFeedViewModel : ViewModel() {


    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }

    private val initState = NewsFeedScreenState.Posts(initList.toList())
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initState)

    val screenState : LiveData<NewsFeedScreenState> = _screenState


    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(oldPosts)
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        val feedPostItem = oldPosts.getItemById(feedPost.id)
        val oldStatistics = feedPostItem.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if(oldItem.type == statisticItem.type){
                    oldItem.copy(count = oldItem.count + 1)
                }else{
                    oldItem
                }
            }
        }
        //val newFeedPostItem = feedPostItem.copy(statistics = newStatistics)
        val newFeedPostList = oldPosts.apply {
            replaceAll { oldItem ->
                if(oldItem.id == feedPost.id){
                    oldItem.copy(statistics = newStatistics)
                }else{
                    oldItem
                }

            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newFeedPostList)

    }
}