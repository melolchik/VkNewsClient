package ru.melolchik.vknewsclient.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.melolchik.vknewsclient.MainViewModel
import ru.melolchik.vknewsclient.domain.FeedPost

@Composable
fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
){

    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when(val currentState = screenState.value){
        is HomeScreenState.Posts -> {
            FeedPosts(posts = currentState.posts, viewModel = viewModel, paddingValues = paddingValues )
        }
        is HomeScreenState.Comments ->{
            CommentsScreen(feedPost = currentState.feedPost, comments = currentState.comments){
                viewModel.closeComments()
            }
            BackHandler {
                viewModel.closeComments()
            }
        }
        HomeScreenState.Initial -> {

        }
    }


}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>
){
    LazyColumn(modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts, key = {it.id }){ feedPost ->
            val dismissState = rememberDismissState()

            if(dismissState.isDismissed(DismissDirection.EndToStart)){
                viewModel.deleteItem(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    Box(modifier = Modifier
                        .padding(16.dp)
                        .background(color = Color.Red.copy(alpha = 0.5f))
                        .fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ){
                        Text(text = "Delete Item",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White)
                    }
                },
                dismissContent = {
                    PostCard(modifier = Modifier,
                        feedPost = feedPost,
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) },
                        onCommentsClickListener = { statisticItem ->
                           // viewModel.updateStatistics(feedPost,statisticItem)
                                                  viewModel.showComments(feedPost)
                                                  },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) }
                    )

                },
                directions = setOf(DismissDirection.EndToStart))

        }
    }
}