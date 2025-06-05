package com.example.catapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.catapp.domain.models.navigation.Screen
import com.example.catapp.presentation.components.BottomNavBar
import com.example.catapp.presentation.explore.ViewModel.ExploreViewModel
import com.example.catapp.presentation.explore.view.ExploreView
import com.example.catapp.presentation.fav.ViewModel.FavViewModel
import com.example.catapp.presentation.fav.view.FavView
import com.example.catapp.presentation.home.ViewModel.HomeViewModel
import com.example.catapp.presentation.home.view.HomeView

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val exploreViewModel: ExploreViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val favViewModel: FavViewModel = hiltViewModel()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        val graph = navController.createGraph(
            startDestination = Screen.Home.route
        ){
            composable(Screen.Home.route) {
                HomeView(homeViewModel)
            }
            composable(Screen.Explore.route) {
                ExploreView(exploreViewModel)
            }
            composable(Screen.Fav.route) {
                FavView(favViewModel)
            }

        }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
}