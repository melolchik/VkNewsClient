package ru.melolchik.vknewsclient.navigation

import ru.melolchik.vknewsclient.domain.FeedPost

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost : FeedPost) : String {

            return "$ROUTE_FOR_ARGS/${feedPost.id}"
        }
    }


    private companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{feed_post_id}"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"

    }
}