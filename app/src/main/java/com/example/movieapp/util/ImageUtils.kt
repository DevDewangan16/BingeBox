package com.example.movieapp.util

object ImageUtils {

    fun createPlaceholderUrl(title: String, width: Int = 300, height: Int = 450): String {
        val encodedTitle = title.replace(" ", "+").take(15)

        // Create beautiful gradient colors based on title hash
        val colors = listOf(
            "1a237e" to "ffffff", // Dark blue to white
            "311b92" to "ffffff", // Deep purple to white
            "004d40" to "ffffff", // Dark teal to white
            "bf360c" to "ffffff", // Deep orange to white
            "1b5e20" to "ffffff", // Dark green to white
            "4a148c" to "ffffff"  // Deep purple to white
        )

        val colorIndex = title.hashCode().absoluteValue() % colors.size
        val colorPair = colors[colorIndex]

        return "https://via.placeholder.com/${width}x${height}/${colorPair.first}/${colorPair.second}?text=$encodedTitle"
    }

    private fun Int.absoluteValue(): Int = if (this < 0) -this else this
}