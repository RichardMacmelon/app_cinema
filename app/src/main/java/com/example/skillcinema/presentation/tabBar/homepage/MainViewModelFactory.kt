package com.example.skillcinema.presentation.tabBar.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val homeViewModel: HomeViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return homeViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}