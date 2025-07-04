package ru.melolchik.vknewsclient.ui.theme

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.melolchik.vknewsclient.NewsFeedViewModel
import ru.melolchik.vknewsclient.domain.FeedPost
import ru.melolchik.vknewsclient.navigation.AppNavGraph
import ru.melolchik.vknewsclient.navigation.Screen
import ru.melolchik.vknewsclient.navigation.rememberNavigateState

fun log(text: String) {
    Log.d("COMPOSE_TEST", text)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {

    val navigationState = rememberNavigateState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items =
                    listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                items.forEachIndexed { index, item ->
//                    log("item = ${item.screen.route}")
//                    navBackStackEntry?.destination?.hierarchy?.forEach {
//                        log("hierarchy item = ${it.route}")
//                    }
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if(!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
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
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        }

    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(paddingValues = paddingValues) {
                    navigationState.navigateToComment(it)
                }

            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                                    },
                    feedPost = feedPost
                )
            },
            favoriteScreenContent = {
                TextCounter(text = "Favorite")
            },
            profileScreenContent = {
                TextCounter(text = "Profile")
            })
    }
}

@Composable
fun TextCounter(text: String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier
            .padding(16.dp)
            .clickable { count++ },
        text = "Name = $text count = $count",
        color = MaterialTheme.colorScheme.onPrimary
    )
}