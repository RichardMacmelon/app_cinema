package com.example.skillcinema.presentation.tabBar.search.searchPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.EntitySearchDataMovieDto
import com.example.skillcinema.domain.GetSearchMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPageViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase
) : ViewModel() {

    private val _infoMovie = MutableStateFlow<List<EntitySearchDataMovieDto>>(emptyList())
    val infoMovie = _infoMovie.asStateFlow()

    private var jobSearchMovie: Job? = null

    fun searchMovie(keyword: String) {
        jobSearchMovie?.cancel()
        if (keyword.isNotEmpty()) {
            jobSearchMovie = viewModelScope.launch(Dispatchers.IO) {
                delay(2000)
                kotlin.runCatching {
                    getSearchMovieUseCase.getSearchMovie(keyword, 1)
                }.fold(
                    onSuccess = {
                        _infoMovie.value = it
                    },
                    onFailure = {
                        Log.d("SearchPageViewModel", it.message ?: "")
                    }
                )
            }
        }
    }
}