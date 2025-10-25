package com.example.movieapp.data.repository

import com.example.movieapp.data.model.TitleItem
import com.example.movieapp.data.network.RetrofitInstance
import com.example.movieapp.util.Constants
import io.reactivex.rxjava3.core.Single

class MovieRepository {
    private val api = RetrofitInstance.api

    fun fetchMoviesAndShows(): Single<Pair<List<TitleItem>, List<TitleItem>>> {
        val moviesCall = api.getPopularMovies(Constants.API_KEY)
            .flatMap { response ->
                if (response.titles.isEmpty()) {
                    Single.just(emptyList())
                } else {
                    // Fetch details for first 20 movies only to avoid rate limiting
                    val detailCalls = response.titles.take(20).map { item ->
                        api.getTitleDetails(item.id, Constants.API_KEY)
                            .map { details ->
                                TitleItem(
                                    id = details.id,
                                    title = details.title,
                                    year = details.year,
                                    type = "movie",
                                    poster = details.poster,
                                    backdrop = details.backdrop,
                                    plot_overview = details.plot_overview,
                                    release_date = details.release_date,
                                    genre_names = details.genre_names,
                                    user_rating = details.user_rating
                                )
                            }
                            .onErrorReturn {
                                // Return original item if details fail
                                TitleItem(
                                    id = item.id,
                                    title = item.title,
                                    year = item.year,
                                    type = item.type,
                                    poster = item.poster,
                                    backdrop = null,
                                    plot_overview = item.plot_overview,
                                    release_date = item.release_date,
                                    genre_names = null,
                                    user_rating = null
                                )
                            }
                    }
                    Single.zip(detailCalls) { results ->
                        results.map { it as TitleItem }
                    }
                }
            }

        val showsCall = api.getPopularTvShows(Constants.API_KEY)
            .flatMap { response ->
                if (response.titles.isEmpty()) {
                    Single.just(emptyList())
                } else {
                    // Fetch details for first 20 shows only to avoid rate limiting
                    val detailCalls = response.titles.take(20).map { item ->
                        api.getTitleDetails(item.id, Constants.API_KEY)
                            .map { details ->
                                TitleItem(
                                    id = details.id,
                                    title = details.title,
                                    year = details.year,
                                    type = "tv_series",
                                    poster = details.poster,
                                    backdrop = details.backdrop,
                                    plot_overview = details.plot_overview,
                                    release_date = details.release_date,
                                    genre_names = details.genre_names,
                                    user_rating = details.user_rating
                                )
                            }
                            .onErrorReturn {
                                // Return original item if details fail
                                TitleItem(
                                    id = item.id,
                                    title = item.title,
                                    year = item.year,
                                    type = item.type,
                                    poster = item.poster,
                                    backdrop = null,
                                    plot_overview = item.plot_overview,
                                    release_date = item.release_date,
                                    genre_names = null,
                                    user_rating = null
                                )
                            }
                    }
                    Single.zip(detailCalls) { results ->
                        results.map { it as TitleItem }
                    }
                }
            }

        return Single.zip(moviesCall, showsCall) { movies, shows ->
            Pair(movies, shows)
        }
    }

    fun getTitleDetails(id: Int): Single<TitleItem> {
        return api.getTitleDetails(id, Constants.API_KEY)
            .map { details ->
                TitleItem(
                    id = details.id,
                    title = details.title,
                    year = details.year,
                    type = "movie",
                    poster = details.poster,
                    backdrop = details.backdrop,
                    plot_overview = details.plot_overview,
                    release_date = details.release_date,
                    genre_names = details.genre_names,
                    user_rating = details.user_rating
                )
            }
    }
}