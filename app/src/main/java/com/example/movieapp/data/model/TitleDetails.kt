package com.example.movieapp.data.model

data class TitleDetails(
    val id: Int,
    val title: String,
    val year: Int?,
    val plot_overview: String?,
    val release_date: String?,
    val poster: String?,
    val backdrop: String?,
    val genre_names: List<String>?,
    val user_rating: Double?,
    val runtime_minutes: Int?
)