package com.example.catapp.domain.models.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("Home")
    data object Fav: Screen("Fav")
    data object Explore: Screen("Explore")
    data object Funny: Screen("Funny")
}