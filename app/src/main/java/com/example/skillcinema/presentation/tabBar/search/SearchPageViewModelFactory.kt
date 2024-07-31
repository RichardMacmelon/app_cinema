package com.example.skillcinema.presentation.tabBar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skillcinema.presentation.tabBar.filmpage.FilmPageViewModel
import javax.inject.Inject

class SearchPageViewModelFactory @Inject constructor(private val searchPageViewModel: SearchPageViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchPageViewModel::class.java)) {
            return searchPageViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}