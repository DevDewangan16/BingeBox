package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.TitleItem
import com.example.movieapp.data.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()
    private val disposables = CompositeDisposable()

    private val _movies = MutableStateFlow<List<TitleItem>>(emptyList())
    val movies: StateFlow<List<TitleItem>> = _movies

    private val _tvShows = MutableStateFlow<List<TitleItem>>(emptyList())
    val tvShows: StateFlow<List<TitleItem>> = _tvShows

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchData()
    }

    fun fetchData() {
        _loading.value = true
        val disposable = repository.fetchMoviesAndShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pair ->
                _movies.value = pair.first
                _tvShows.value = pair.second
                _loading.value = false
            }, { throwable ->
                _error.value = throwable.message
                _loading.value = false
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
