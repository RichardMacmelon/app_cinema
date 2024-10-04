package com.example.skillcinema.presentation.tabBar.profile.profileDialogPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDialogueViewModel @Inject constructor(
    private val dbUseCase: DBUseCase
) : ViewModel() {

    fun addCollection(collectionName: String) {
        viewModelScope.launch {
            dbUseCase.insertCollection(
                CollectionDB(
                    collectionName = collectionName,
                    collectionSize = 0
                )
            )
        }
    }
}