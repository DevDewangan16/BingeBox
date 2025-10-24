package com.example.movieapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Title
import com.example.movieapp.ui.common.ShimmerBrush
import com.example.movieapp.util.ImageUtils
import com.example.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navController: NavController,
    titleId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = titleId) {
        // Only load details if we have a valid ID
        if (titleId > 0) {
            viewModel.loadTitleDetails(titleId)
        }
    }

    MovieAppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Details",
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is DetailsUiState.Loading -> {
                        // Keep the shimmer effect but ensure it stops when data loads
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Shimmer for poster
                            Box(
                                modifier = Modifier
                                    .size(300.dp, 450.dp)
                                    .background(
                                        ShimmerBrush(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            // Shimmer for title
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(32.dp)
                                    .background(
                                        ShimmerBrush(),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            // Shimmer for info
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(16.dp)
                                        .background(
                                            ShimmerBrush(),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    is DetailsUiState.Success -> {
                        val title = (uiState as DetailsUiState.Success).title
                        DetailsContent(
                            title = title,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        )
                    }

                    is DetailsUiState.Error -> {
                        val errorMessage = (uiState as DetailsUiState.Error).message
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Error Loading Details",
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
                                    onClick = {
                                        // Go back to home instead of retrying (since data might not be loaded)
                                        navController.popBackStack()
                                    }
                                ) {
                                    Text("Go Back to Home")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsContent(
    title: Title,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Beautiful placeholder image
        AsyncImage(
            model = ImageUtils.createPlaceholderUrl(title.title, 300, 450),
            contentDescription = "${title.title} poster",
            modifier = Modifier
                .size(300.dp, 450.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = title.title,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoChip("Year", title.year?.toString() ?: "N/A")
            InfoChip("Type", title.type.replace("_", " ").uppercase())
            InfoChip("ID", title.id.toString())
        }

        // Additional info row
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            title.imdbId?.let { imdbId ->
                InfoChip("IMDb", imdbId)
            }
            title.tmdbId?.let { tmdbId ->
                InfoChip("TMDB", tmdbId.toString())
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Description
        Text(
            text = "About",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${title.title} is a ${title.type.replace("_", " ")} released in ${title.year ?: "unknown year"}. " +
                    "This information is fetched from Watchmode API in real-time using RxJava Single.zip for simultaneous API calls.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun InfoChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}