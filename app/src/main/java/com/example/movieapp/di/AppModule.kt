package com.example.movieapp.di

import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.details.DetailsViewModel
import com.example.movieapp.ui.home.HomeViewModel

object AppModule {

    private val movieRepository by lazy { MovieRepository() }

    fun provideHomeViewModel(): HomeViewModel {
        return HomeViewModel(movieRepository)
    }

    fun provideDetailsViewModel(): DetailsViewModel {
        return DetailsViewModel(movieRepository)
    }
}