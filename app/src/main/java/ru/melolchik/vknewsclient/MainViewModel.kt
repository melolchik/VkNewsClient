package ru.melolchik.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }
    private val _feedPostList = MutableLiveData( initList.toList())

    val feedPostList : LiveData<List<FeedPost>> = _feedPostList

    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val oldList = _feedPostList.value?.toMutableList() ?: mutableListOf()
        oldList.remove(feedPost)
        _feedPostList.value = oldList
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val feedPostItem = _feedPostList.value?.getItemById(feedPost.id) ?: throw IllegalArgumentException("FeedPost not found!")
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
        val newFeedPostList = _feedPostList.value?.toMutableList()?.apply {
            replaceAll { oldItem ->
                if(oldItem.id == feedPost.id){
                    oldItem.copy(statistics = newStatistics)
                }else{
                    oldItem
                }

            }
        }
        _feedPostList.value = newFeedPostList

    }
}