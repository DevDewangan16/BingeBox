package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.details.DetailsScreen
import com.example.movieapp.ui.home.HomeScreen
import com.example.movieapp.ui.home.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val homeViewModel: HomeViewModel = viewModel()

            MaterialTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(homeViewModel, navController)
                    }
                    composable("details/{title}/{desc}/{date}/{poster}") { backStackEntry ->
                        val args = backStackEntry.arguments
                        DetailsScreen(
                            title = args?.getString("title") ?: "",
                            desc = args?.getString("desc") ?: "",
                            date = args?.getString("date") ?: "",
                            poster = args?.getString("poster") ?: ""
                        )
                    }
                }
            }
        }
    }
}
