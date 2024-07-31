package com.example.skillcinema.presentation.tabBar.similarMoviePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SimilarMovieViewModelFactory @Inject constructor(
    private val similarMovieViewModel: SimilarMovieViewModel
) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(similarMovieViewModel::class.java)) {
            return similarMovieViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}