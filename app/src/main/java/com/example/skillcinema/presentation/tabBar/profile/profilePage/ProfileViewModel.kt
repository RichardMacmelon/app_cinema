package com.example.skillcinema.presentation.tabBar.profile.profilePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dbUseCase: DBUseCase
) : ViewModel() {

    init {
        updateCollectionSize()
    }

    val allCollections = this.dbUseCase.getAllCollection().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val allInfo = this.dbUseCase.getAllInfo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val viewedFilms = this.dbUseCase.getFilmsFlowByCollectionId(1).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val interestingFilms = this.dbUseCase.getFilmsFlowByCollectionId(2).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun deleteCollection(collectionId: Int) {
        viewModelScope.launch {
            dbUseCase.deleteCollection(collectionId)
        }
    }

    fun deleteAllFilmsInCollectionId(collectionId: Int) {
        viewModelScope.launch {
            dbUseCase.deleteAllFilmsInCollectionId(collectionId)
        }
    }

    private fun updateCollectionSize() {
        viewModelScope.launch {
            dbUseCase.getListCollectionId().forEach { id ->
                val size = dbUseCase.countForCollection(id)
                dbUseCase.updateCollectionSize(id, size)
            }
        }
    }
}