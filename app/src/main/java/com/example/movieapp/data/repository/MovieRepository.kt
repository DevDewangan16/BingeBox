package com.example.movieapp.data.repository

import com.example.movieapp.data.model.TitleItem
import com.example.movieapp.data.network.RetrofitInstance
import com.example.movieapp.util.Constants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class MovieRepository {
    private val api = RetrofitInstance.api

    fun fetchMoviesAndShows(): Single<Pair<List<TitleItem>, List<TitleItem>>> {
        val moviesCall = api.getPopularMovies(Constants.API_KEY)
            .flatMap { response ->
                val detailSingles = response.titles.map { item ->
                    api.getTitleDetails(item.id, Constants.API_KEY)
                        .map { details ->
                            TitleItem(
                                id = details.id,
                                title = details.title,
                                year = details.year,
                                type = "movie",
                                poster = details.poster,
                                plot_overview = details.plot_overview,
                                release_date = details.release_date
                            )
                        }
                }
                if (detailSingles.isEmpty()) Single.just(emptyList())
                else Single.zip(detailSingles) { it.toList() as List<TitleItem> }
            }

        val showsCall = api.getPopularTvShows(Constants.API_KEY)
            .flatMap { response ->
                val detailSingles = response.titles.map { item ->
                    api.getTitleDetails(item.id, Constants.API_KEY)
                        .map { details ->
                            TitleItem(
                                id = details.id,
                                title = details.title,
                                year = details.year,
                                type = "tv_series",
                                poster = details.poster,
                                plot_overview = details.plot_overview,
                                release_date = details.release_date
                            )
                        }
                }
                if (detailSingles.isEmpty()) Single.just(emptyList())
                else Single.zip(detailSingles) { it.toList() as List<TitleItem> }
            }

        return Single.zip(moviesCall, showsCall, BiFunction { m, s -> Pair(m, s) })
    }
}
