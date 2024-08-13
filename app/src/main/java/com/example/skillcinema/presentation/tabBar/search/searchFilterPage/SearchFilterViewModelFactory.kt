package com.example.skillcinema.presentation.tabBar.search.searchFilterPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SearchFilterViewModelFactory @Inject constructor(private val searchFilterViewModel: SearchFilterViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFilterViewModel::class.java)) {
            return searchFilterViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}