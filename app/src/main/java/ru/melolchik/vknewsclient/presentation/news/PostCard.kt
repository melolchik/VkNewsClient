package ru.melolchik.vknewsclient.presentation.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.melolchik.vknewsclient.R
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.domain.StatisticItem
import ru.melolchik.vknewsclient.domain.StatisticType


@Composable
fun PostCard(
    modifier: Modifier = Modifier, feedPost: FeedPost,
    onViewsClickListener: (item: StatisticItem) -> Unit,
    onShareClickListener: (item: StatisticItem) -> Unit,
    onCommentsClickListener: (item: StatisticItem) -> Unit,
    onLikeClickListener: (item: StatisticItem) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier.fillMaxWidth(),
                //painter = painterResource(id = feedPost.contentImageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                feedPost.statistics,

                onViewsClickListener,
                onShareClickListener,
                onCommentsClickListener,
                onLikeClickListener)
        }

    }

}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onViewsClickListener: (item: StatisticItem) -> Unit,
    onShareClickListener: (item: StatisticItem) -> Unit,
    onCommentsClickListener: (item: StatisticItem) -> Unit,
    onLikeClickListener: (item: StatisticItem) -> Unit
) {
    Row {
        Row(modifier = Modifier.weight(1f))
        {
            val viewItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                viewItem.count.toString()
            ) { onViewsClickListener(viewItem) }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val shareItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                shareItem.count.toString()
            ) {
                onShareClickListener(shareItem)
            }
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                commentsItem.count.toString()
            ) {
                onCommentsClickListener(commentsItem)
            }
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_like,
                likesItem.count.toString()
            ) {
                onLikeClickListener(likesItem)
            }
        }

    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalArgumentException()
}

@Composable
private fun IconWithText(
    iconResId: Int, text: String,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                text = feedPost.communityName + " id = " + feedPost.id,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPost.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

