package ru.melolchik.vknewsclient.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class FeedPost(
    val id: Long,
    val communityId : Long,
    val communityName: String,
    val publicationDate: String = "14:00",
    val communityImageUrl: String,
    val contentText: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    val contentImageUrl: String?,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 234),
        StatisticItem(StatisticType.COMMENTS, 113),
        StatisticItem(StatisticType.SHARES, 34),
        StatisticItem(StatisticType.LIKES, 243)
    ),
    val isLiked : Boolean = Random.nextBoolean()
) : Parcelable{

    companion object{

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false){
            override fun get(
                bundle: Bundle,
                key: String
            ): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson<FeedPost>(value, FeedPost::class.java)
            }

            override fun put(
                bundle: Bundle,
                key: String,
                value: FeedPost
            ) {
                bundle.putParcelable(key,value)
            }

        }
    }
}


