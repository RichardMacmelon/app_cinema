package com.example.skillcinema.presentation.tabBar.profile.allFilmInCollectionPage

import androidx.lifecycle.ViewModel
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllFilmInCollectionViewModel @Inject constructor(
    private val dbUseCase: DBUseCase
) : ViewModel() {

    suspend fun loadFilm(collectionId: Int): List<FilmDB> {
        return dbUseCase.getFilmsByCollectionId(collectionId)
    }

    suspend fun loadCollectionName(collectionId: Int): String {
        return dbUseCase.getCollectionNameById(collectionId)
    }
}