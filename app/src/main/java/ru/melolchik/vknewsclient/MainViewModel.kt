package ru.melolchik.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())

    val feedPost : LiveData<FeedPost> = _feedPost

    public fun updateStatistics(item: StatisticItem){
        val oldStatistics = feedPost.value.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if(oldItem.type == item.type){
                    oldItem.copy(count = oldItem.count + 1)
                }else{
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value.copy(statistics = newStatistics)
    }
}