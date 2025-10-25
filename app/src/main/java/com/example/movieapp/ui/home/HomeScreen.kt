package com.example.movieapp.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.model.TitleItem
import com.example.movieapp.util.ShimmerListItem
import com.valentinilk.shimmer.shimmer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val movies by viewModel.movies.collectAsState()
    val shows by viewModel.tvShows.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showMovies by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watchmode Discovery") },
                actions = {
                    TextButton(onClick = { showMovies = !showMovies }) {
                        Text(if (showMovies) "Movies" else "TV Shows")
                    }
                }
            )
        },
        snackbarHost = {
            error?.let {
                Snackbar { Text(text = it) }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            if (loading) {
                // Show 6 shimmer placeholders
                items(6) {
                    ShimmerListItem(isLoading = true) {
                        // Empty content, just shimmer
                    }
                }
            } else {
                val list = if (showMovies) movies else shows
                items(list) { item ->
                    ShimmerListItem(isLoading = false) {
                        MovieItem(item) {
                            navController.navigate(
                                "details/${encodeArg(item.title)}/${encodeArg(item.plot_overview)}/${encodeArg(item.release_date)}/${encodeArg(item.poster)}"
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun MovieItem(item: TitleItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.poster ?: ""),
            contentDescription = item.title,
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.align(Alignment.CenterVertically)) {
            Text(item.title, fontWeight = FontWeight.Bold)
            Text("(${item.year ?: "N/A"})")
        }
    }
}

fun encodeArg(arg: String?): String =
    URLEncoder.encode(arg ?: "", StandardCharsets.UTF_8.toString())