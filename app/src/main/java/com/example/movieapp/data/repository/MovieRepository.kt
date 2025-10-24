package com.example.movieapp.data.repository

import android.util.Log
import com.example.movieapp.data.api.ApiConstants
import com.example.movieapp.data.api.RetrofitClient
import com.example.movieapp.data.model.*
import io.reactivex.rxjava3.core.Single

class MovieRepository {

    private val apiService = RetrofitClient.apiService
    private val TAG = "MovieRepository"

    // Cache for all loaded titles
    private var allTitles: List<Title> = emptyList()
    private var isDataLoaded = false

    fun getMoviesAndTVShows(): Single<Pair<List<Title>, List<Title>>> {
        Log.d(TAG, "Fetching movies and TV shows from API")

        return Single.zip(
            apiService.getMovies(apiKey = ApiConstants.API_KEY)
                .doOnSuccess { response ->
                    Log.d(TAG, "Movies loaded: ${response.titles.size}")
                }
                .onErrorReturn { error ->
                    Log.e(TAG, "Movies API Error: ${error.message}")
                    MovieResponse(emptyList())
                },
            apiService.getTVShows(apiKey = ApiConstants.API_KEY)
                .doOnSuccess { response ->
                    Log.d(TAG, "TV Shows loaded: ${response.titles.size}")
                }
                .onErrorReturn { error ->
                    Log.e(TAG, "TV Shows API Error: ${error.message}")
                    TVShowResponse(emptyList())
                },
            { moviesResponse, tvShowsResponse ->
                // Combine all titles for details lookup
                allTitles = moviesResponse.titles + tvShowsResponse.titles
                isDataLoaded = true
                Log.d(TAG, "✅ Total titles cached: ${allTitles.size}")
                Pair(moviesResponse.titles, tvShowsResponse.titles)
            }
        )
    }

    fun getTitleDetails(id: Int): Single<Title> {
        Log.d(TAG, "Getting details for ID: $id, Data loaded: $isDataLoaded")

        return if (isDataLoaded && allTitles.isNotEmpty()) {
            // Find the title in our cached data
            val title = allTitles.find { it.id == id }

            if (title != null) {
                Log.d(TAG, "✅ Found title in cache: ${title.title}")
                Single.just(title) // Remove delay - it's causing issues
            } else {
                Log.e(TAG, "❌ Title not found in cache for ID: $id")
                Single.error(Exception("Title not found"))
            }
        } else {
            Log.e(TAG, "❌ Data not loaded yet or cache empty for ID: $id")
            Single.error(Exception("Please wait for data to load first"))
        }
    }
}