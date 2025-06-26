package ru.melolchik.vknewsclient.ui.theme

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.melolchik.vknewsclient.MainViewModel
import ru.melolchik.vknewsclient.domain.FeedPost

fun log(text : String){
    Log.d("COMPOSE_TEST", text)
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel){


    Scaffold (
        bottomBar = {
            NavigationBar {

                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }
                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }

    ){ paddingValues ->
        val feedPostList = viewModel.feedPostList.observeAsState(initial = listOf())
        LazyColumn(modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            items(feedPostList.value, key = {it.id }){ feedPost ->
                val dismissState = rememberDismissState()

                if(dismissState.isDismissed(DismissDirection.EndToStart)){
                    viewModel.deleteItem(feedPost)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {
                        Box(modifier = Modifier.padding(16.dp)
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
                                viewModel.updateStatistics(feedPost,statisticItem) },
                            onLikeClickListener = { statisticItem ->
                                viewModel.updateStatistics(feedPost,statisticItem) }
                        )

                    },
                    directions = setOf(DismissDirection.EndToStart))

            }
        }

    }
}