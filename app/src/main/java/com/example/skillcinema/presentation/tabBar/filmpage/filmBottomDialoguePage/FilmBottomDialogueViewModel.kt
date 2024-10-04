package com.example.skillcinema.presentation.tabBar.filmpage.filmBottomDialoguePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmBottomDialogueViewModel @Inject constructor(
    private val dbUseCase: DBUseCase
) : ViewModel() {

    val allCollections = this.dbUseCase.getAllCollection().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    private var listTest = emptyList<Boolean>().toMutableList()

    fun insertToCollection(film: FilmDB) {
        viewModelScope.launch {
            dbUseCase.insertFilm(film)
            updateCollectionSize()
        }
    }

    fun deleteFilmInCollection(film: FilmDB) {
        viewModelScope.launch {
            dbUseCase.deleteFilm(film)
            updateCollectionSize()
        }
    }

    suspend fun checkBoxStates(film: FilmDB): MutableList<Boolean> {
        dbUseCase.getListCollectionId().forEach { collectionId ->
            val filmWithNewCollectionId = FilmDB(
                collectionId = collectionId,
                filmId = film.filmId,
                filmName = film.filmName,
                filmGenre = film.filmGenre,
                filmPoster = film.filmPoster,
                filmRating = film.filmRating,
                filmYear = film.filmYear
            )
            listTest.add(checkFilmIntoCollection(filmWithNewCollectionId))
        }
        return listTest
    }

    private suspend fun checkFilmIntoCollection(film: FilmDB): Boolean {
        return dbUseCase.checkFilmIntoCollection(film)
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