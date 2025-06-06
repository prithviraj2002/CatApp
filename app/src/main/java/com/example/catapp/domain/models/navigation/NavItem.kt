package com.example.catapp.domain.models.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    NavItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = Screen.Home.route
    ),
    NavItem(
        title = "Explore",
        icon = Icons.Default.Search,
        route = Screen.Explore.route
    ),
    NavItem(
        title = "Images",
        icon = Icons.Default.AccountBox,
        route = Screen.Funny.route
    ),
    NavItem(
        title = "Favourites",
        icon = Icons.Default.Favorite,
        route = Screen.Fav.route
    ),
)
