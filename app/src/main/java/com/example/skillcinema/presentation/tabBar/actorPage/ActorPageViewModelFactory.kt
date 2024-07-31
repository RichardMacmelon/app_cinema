package com.example.skillcinema.presentation.tabBar.actorPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ActorPageViewModelFactory @Inject constructor(private val actorPageViewModel: ActorPageViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorPageViewModel::class.java)) {
            return actorPageViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}