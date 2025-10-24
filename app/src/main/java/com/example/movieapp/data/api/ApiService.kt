package com.example.movieapp.data.api

import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.model.TVShowResponse
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list-titles/")
    fun getMovies(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "movie",
        @Query("limit") limit: Int = 20,
        @Query("sort_by") sortBy: String = "popularity_desc"
    ): Single<MovieResponse>

    @GET("list-titles/")
    fun getTVShows(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "tv_series",
        @Query("limit") limit: Int = 20,
        @Query("sort_by") sortBy: String = "popularity_desc"
    ): Single<TVShowResponse>

    // New endpoint to get images
    @GET("autocomplete-search/")
    fun searchTitles(
        @Query("apiKey") apiKey: String,
        @Query("search_value") searchValue: String,
        @Query("search_type") searchType: Int = 1 // 1 for titles
    ): Single<SearchResponse>
}

data class SearchResponse(
    val results: List<SearchResult>
)

data class SearchResult(
    val id: Int,
    val name: String,
    val type: String,
    val year: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("tmdb_id")
    val tmdbId: Int?
)

object ApiConstants {
    const val BASE_URL = "https://api.watchmode.com/v1/"
    const val API_KEY = "Evjg5VrfIUSNootxruUa5GyXv6dzB9R14dvSkZ1b"
}