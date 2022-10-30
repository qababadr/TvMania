package com.dev.tvmania.util

sealed class Screen(val route: String){

    object HomeScreen: Screen(route = "home")

    object TvShowDetailScreen: Screen(route = "tvShow/{tvShowId}")

}
