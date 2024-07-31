package com.example.skillcinema.presentation.tabBar.galleryPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class GalleryPageViewModelFactory @Inject constructor(
    private val galleryPageViewModel: GalleryPageViewModel
) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(galleryPageViewModel::class.java)) {
            return galleryPageViewModel as T
        }
        throw java.lang.IllegalArgumentException("Unknown class name")
    }
}