package com.example.skillcinema.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dbUseCase: DBUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (!dbUseCase.checkCollection(1)) {
                addStandardCollection()
            }
        }
    }

    private fun addStandardCollection() {
        val collectionViewed = CollectionDB(collectionName = "Просмотрено", collectionSize = 0)
        val collectionInteresting = CollectionDB(collectionName = "Интересные", collectionSize = 0)
        val collectionFavorites = CollectionDB(collectionName = "Любимые", collectionSize = 0)
        val collectionWantSee = CollectionDB(collectionName = "Хочу посмотреть", collectionSize = 0)
        viewModelScope.launch {
            dbUseCase.let {
                it.insertCollection(collectionViewed)
                it.insertCollection(collectionInteresting)
                it.insertCollection(collectionFavorites)
                it.insertCollection(collectionWantSee)
            }
        }
    }
}