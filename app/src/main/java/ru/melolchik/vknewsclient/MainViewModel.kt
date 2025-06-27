package ru.melolchik.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.PostComment
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val comments = mutableListOf<PostComment>().apply {
        repeat(20){
            add(PostComment(it))
        }
    }

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }

    private val initState = HomeScreenState.Posts(initList.toList())
    private val _screenState = MutableLiveData<HomeScreenState>(initState)

    val screenState : LiveData<HomeScreenState> = _screenState

    private var savedState :HomeScreenState? = initState

    fun showComments(feedPost: FeedPost){
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }

    fun closeComments(){
        _screenState.value = savedState
    }
    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(oldPosts)
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.Posts){
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
        _screenState.value = HomeScreenState.Posts(newFeedPostList)

    }
}