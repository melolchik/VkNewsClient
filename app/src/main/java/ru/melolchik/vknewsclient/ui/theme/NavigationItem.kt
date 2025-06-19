package ru.melolchik.vknewsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.melolchik.vknewsclient.R

sealed class NavigationItem(
    val titleResId : Int,
    val icon: ImageVector
){
    object Home : NavigationItem (R.string.navigation_item_main, Icons.Outlined.Home)
    object Favorite : NavigationItem (R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem (R.string.navigation_item_profile, Icons.Outlined.Person)
}