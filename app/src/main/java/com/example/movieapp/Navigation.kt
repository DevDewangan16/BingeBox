package com.example.movieapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.di.AppModule
import com.example.movieapp.ui.details.DetailsScreen
import com.example.movieapp.ui.home.HomeScreen

@Composable
fun MovieAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = AppModule.provideHomeViewModel(),
                navController = navController
            )
        }
        composable(
            "details/{titleId}",
            arguments = listOf(navArgument("titleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val titleId = backStackEntry.arguments?.getInt("titleId") ?: 0
            DetailsScreen(
                viewModel = AppModule.provideDetailsViewModel(),
                navController = navController,
                titleId = titleId
            )
        }
    }
}