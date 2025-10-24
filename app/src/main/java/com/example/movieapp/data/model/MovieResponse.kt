package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("titles")
    val titles: List<Title> = emptyList(),
    val page: Int = 1,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)

data class TVShowResponse(
    @SerializedName("titles")
    val titles: List<Title> = emptyList(),
    val page: Int = 1,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)

data class Title(
    val id: Int,
    val title: String,
    val type: String,
    val year: Int?,
    @SerializedName("year_end")
    val yearEnd: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("tmdb_id")
    val tmdbId: Int?,
    @SerializedName("tmdb_type")
    val tmdbType: String?,
    @SerializedName("image_url")
    val imageUrl: String? = null // Add image_url field
)