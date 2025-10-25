package com.example.movieapp.data.network

import com.example.movieapp.data.model.TitleDetails
import com.example.movieapp.data.model.TitleResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list-titles/")
    fun getPopularMovies(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "movie",
        @Query("limit") limit: Int = 50,
        @Query("sort_by") sortBy: String = "popularity_desc"
    ): Single<TitleResponse>

    @GET("list-titles/")
    fun getPopularTvShows(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "tv_series",
        @Query("limit") limit: Int = 50,
        @Query("sort_by") sortBy: String = "popularity_desc"
    ): Single<TitleResponse>

    @GET("title/{id}/details/")
    fun getTitleDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Single<TitleDetails>
}