package ru.melolchik.vknewsclient.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.melolchik.vknewsclient.R
@Parcelize
data class FeedPost(
    val id: Int = 0,
    val comunityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 234),
        StatisticItem(StatisticType.COMMENTS, 113),
        StatisticItem(StatisticType.SHARES, 34),
        StatisticItem(StatisticType.LIKES, 243)
    )
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


