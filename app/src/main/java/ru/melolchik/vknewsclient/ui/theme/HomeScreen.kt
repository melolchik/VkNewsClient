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
//import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
//import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.melolchik.vknewsclient.NewsFeedViewModel
import ru.melolchik.vknewsclient.domain.FeedPost

@Composable
fun HomeScreen(
    paddingValues : PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
){

    val viewModel : NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentState = screenState.value){
        is NewsFeedScreenState.Posts -> {
            FeedPosts(posts = currentState.posts, viewModel = viewModel,
                paddingValues = paddingValues ,
                onCommentsClickListener = onCommentsClickListener )
        }

        NewsFeedScreenState.Initial -> {

        }
    }


}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentsClickListener: (FeedPost) -> Unit
){
    LazyColumn(modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts, key = {it.id }){ feedPost ->
            val dismissState = rememberSwipeToDismissBoxState()

//            if(dismissState.isDismissed(DismissDirection.EndToStart)){
//                viewModel.deleteItem(feedPost)
//            }
            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                backgroundContent = {
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
                content = {
                    PostCard(modifier = Modifier,
                        feedPost = feedPost,
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) },
                        onCommentsClickListener = { statisticItem ->
                            onCommentsClickListener(feedPost)
                                                  },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost,statisticItem) }
                    )

                }
               // directions = setOf(DismissDirection.EndToStart)
         )

        }
    }
}