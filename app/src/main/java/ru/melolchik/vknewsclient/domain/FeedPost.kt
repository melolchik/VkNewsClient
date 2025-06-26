package ru.melolchik.vknewsclient.domain

import androidx.compose.ui.res.stringResource
import ru.melolchik.vknewsclient.R

data class FeedPost(
    val id: Int,
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
)


