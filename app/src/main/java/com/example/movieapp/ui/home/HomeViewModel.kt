package com.example.movieapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Title
import com.example.movieapp.data.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val TAG = "HomeViewModel"

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow(ContentTab.MOVIES)
    val selectedTab = _selectedTab.asStateFlow()

    private var movies: List<Title> = emptyList()
    private var tvShows: List<Title> = emptyList()

    private val _isDataReady = MutableStateFlow(false)
    val isDataReady: StateFlow<Boolean> = _isDataReady.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = HomeUiState.Loading
        _isDataReady.value = false
        Log.d(TAG, "Starting to load data from API...")

        val disposable = repository.getMoviesAndTVShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (moviesList, tvShowsList) ->
                    Log.d(TAG, "✅ Data received - Movies: ${moviesList.size}, TV Shows: ${tvShowsList.size}")

                    movies = moviesList
                    tvShows = tvShowsList
                    _isDataReady.value = true

                    updateContent()
                },
                { error ->
                    Log.e(TAG, "❌ Error loading data: ${error.message}", error)
                    _uiState.value = HomeUiState.Error(
                        "Failed to load data: ${error.message ?: "Unknown error"}"
                    )
                    _isDataReady.value = false
                }
            )

        disposables.add(disposable)
    }

    fun selectTab(tab: ContentTab) {
        _selectedTab.value = tab
        updateContent()
    }

    private fun updateContent() {
        val content = when (_selectedTab.value) {
            ContentTab.MOVIES -> movies
            ContentTab.TV_SHOWS -> tvShows
        }
        Log.d(TAG, "Updating content for ${_selectedTab.value}: ${content.size} items")
        _uiState.value = HomeUiState.Success(content)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val titles: List<Title>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

enum class ContentTab {
    MOVIES, TV_SHOWS
}