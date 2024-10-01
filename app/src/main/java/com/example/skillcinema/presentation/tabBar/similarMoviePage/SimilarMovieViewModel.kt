package com.example.skillcinema.presentation.tabBar.similarMoviePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.EntitySimilarsFilmsDto
import com.example.skillcinema.domain.useCase.GetSimilarsMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimilarMovieViewModel @Inject constructor(
    private val getSimilarsMovieUseCase: GetSimilarsMovieUseCase
): ViewModel() {

    private val _similarsMovie = MutableSharedFlow<EntitySimilarsFilmsDto>()
    val similarsMovie = _similarsMovie.asSharedFlow()

    fun loadSimilarsMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getSimilarsMovieUseCase.getSimilarsMovie(id)
            }.fold(
                onSuccess = {
                    _similarsMovie.emit(it)
                },
                onFailure = { Log.d("FilmPageViewModel", it.message ?: "") }
            )
        }
    }
}