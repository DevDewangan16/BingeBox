package com.example.movieapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.util.ShimmerListItem
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(title: String, desc: String, date: String, poster: String) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(500) // simulate network delay
        isLoading = false
    }

    val decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8.toString())
    val decodedDesc = URLDecoder.decode(desc, StandardCharsets.UTF_8.toString())
    val decodedDate = URLDecoder.decode(date, StandardCharsets.UTF_8.toString())
    val decodedPoster = URLDecoder.decode(poster, StandardCharsets.UTF_8.toString())

    Scaffold(
        topBar = { TopAppBar(title = { Text(decodedTitle) }) }
    ) { padding ->
        ShimmerListItem(isLoading = isLoading, modifier = Modifier.padding(padding)) {
            Column(Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(decodedPoster),
                    contentDescription = decodedTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(decodedTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Release Date: ${decodedDate.ifEmpty { "N/A" }}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(decodedDesc.ifEmpty { "No description available." })
            }
        }
    }
}
