package com.example.skillcinema.presentation.tabBar.filmpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilmPageViewModelFactory @Inject constructor(private val filmPageViewModel: FilmPageViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmPageViewModel::class.java)) {
            return filmPageViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}