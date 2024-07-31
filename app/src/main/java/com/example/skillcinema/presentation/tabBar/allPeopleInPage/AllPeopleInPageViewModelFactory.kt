package com.example.skillcinema.presentation.tabBar.allPeopleInPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AllPeopleInPageViewModelFactory @Inject constructor(private val allPeopleInPageViewModel: AllPeopleInPageViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllPeopleInPageViewModel::class.java)) {
            return allPeopleInPageViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}