package ru.melolchik.vknewsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.melolchik.vknewsclient.R
import ru.melolchik.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId : Int,
    val icon: ImageVector
){
    object Home : NavigationItem (Screen.Home,R.string.navigation_item_main, Icons.Outlined.Home)
    object Favorite : NavigationItem (Screen.Favorite,R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem (Screen.Profile,R.string.navigation_item_profile, Icons.Outlined.Person)
}