package com.example.movieapp.ui.details

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

class DetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadTitleDetails(id: Int) {
        _uiState.value = DetailsUiState.Loading

        val disposable = repository.getTitleDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { title ->
                    _uiState.value = DetailsUiState.Success(title)
                },
                { error ->
                    _uiState.value = DetailsUiState.Error(
                        "Failed to load details: ${error.message ?: "Unknown error"}"
                    )
                }
            )

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val title: Title) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}