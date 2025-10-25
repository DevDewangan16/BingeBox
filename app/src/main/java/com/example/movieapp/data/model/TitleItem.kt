package com.example.movieapp.data.model

data class TitleItem(
    val id: Int,
    val title: String,
    val year: Int?,
    val type: String,
    val poster: String? = null,
    val plot_overview: String? = null,
    val release_date: String? = null
)
