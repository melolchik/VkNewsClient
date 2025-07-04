package ru.melolchik.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.melolchik.vknewsclient.domain.FeedPost

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route : String){
        navHostController.navigate(route){
            popUpTo(navHostController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComment(feedPost : FeedPost){
        navHostController.navigate(Screen.Comments.getRouteWithArgs(feedPost))// comments/15/
    }
}

@Composable
fun rememberNavigateState(
    navHostController: NavHostController = rememberNavController()
) : NavigationState{
    return remember {
        NavigationState(navHostController)
    }
}