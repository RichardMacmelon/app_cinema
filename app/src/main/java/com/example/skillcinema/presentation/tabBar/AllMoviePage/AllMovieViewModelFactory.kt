package com.example.skillcinema.presentation.tabBar.AllMoviePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skillcinema.presentation.tabBar.homepage.HomeViewModel
import javax.inject.Inject

class AllMovieViewModelFactory @Inject constructor(private val allMovieViewModel: AllMovieViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllMovieViewModel::class.java)) {
            return allMovieViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}