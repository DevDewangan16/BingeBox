package com.example.movieapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.ui.common.ShimmerListItem
import com.example.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isDataReady by viewModel.isDataReady.collectAsState()

    MovieAppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Movie App",
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tab Selection
                TabRow(selectedTabIndex = selectedTab.ordinal) {
                    Tab(
                        selected = selectedTab == ContentTab.MOVIES,
                        onClick = { viewModel.selectTab(ContentTab.MOVIES) },
                        text = { Text("Movies") },
                        icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Movies") }
                    )
                    Tab(
                        selected = selectedTab == ContentTab.TV_SHOWS,
                        onClick = { viewModel.selectTab(ContentTab.TV_SHOWS) },
                        text = { Text("TV Shows") },
                        icon = { Icon(Icons.Default.PlayArrow, contentDescription = "TV Shows") }
                    )
                }

                // Content
                when (uiState) {
                    is HomeUiState.Loading -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(10) {
                                ShimmerListItem(
                                    isLoading = true,
                                    contentAfterLoading = {}
                                )
                            }
                        }
                    }

                    is HomeUiState.Success -> {
                        val titles = (uiState as HomeUiState.Success).titles
                        if (titles.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No ${selectedTab.name.lowercase().replaceFirstChar { it.uppercase() }} found",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(titles) { title ->
                                    TitleItem(
                                        title = title,
                                        onClick = {
                                            // Always allow navigation - data should be ready if we're in Success state
                                            navController.navigate("details/${title.id}")
                                        }
                                    )
                                }
                            }
                        }
                    }

                    is HomeUiState.Error -> {
                        val errorMessage = (uiState as HomeUiState.Error).message
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Error",
                                    style = MaterialTheme.typography.displaySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = errorMessage,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Button(
                                    onClick = { viewModel.loadData() }
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}