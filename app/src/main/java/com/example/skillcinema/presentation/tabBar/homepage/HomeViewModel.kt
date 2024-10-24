package com.example.skillcinema.presentation.tabBar.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import com.example.skillcinema.domain.useCase.GetCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val dbUseCase: DBUseCase
) : ViewModel() {

    private val _popularSeries = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val popularSeries = _popularSeries.asStateFlow()

    private val _movieTop250Collections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieTop250Collections = _movieTop250Collections.asStateFlow()

    private val _moviePopularCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val moviePopularCollections = _moviePopularCollections.asStateFlow()

    private val _movieVampireCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieVampireCollections = _movieVampireCollections.asStateFlow()

    private val _movieFamilyCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieFamilyCollections = _movieFamilyCollections.asStateFlow()

    init {
        loadPopularSeries()
        loadCollectionsTop250()
        loadCollectionsPopular()
        loadCollectionsVampire()
        loadCollectionsFamily()
    }

    private fun loadPopularSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getCollectionsUseCase.getTop250Collections(COMICS_THEME, PAGE)
            }.fold(
                onSuccess = {
                    _popularSeries.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    private fun loadCollectionsTop250() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getCollectionsUseCase.getTop250Collections(TOP_250, PAGE)
            }.fold(
                onSuccess = {
                    _movieTop250Collections.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    private fun loadCollectionsPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getCollectionsUseCase.getTop250Collections(TOP_POPULAR_ALL, PAGE)
            }.fold(
                onSuccess = {
                    _moviePopularCollections.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    private fun loadCollectionsVampire() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getCollectionsUseCase.getTop250Collections(VAMPIRE_THEME, PAGE)
            }.fold(
                onSuccess = {
                    _movieVampireCollections.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    private fun loadCollectionsFamily() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getCollectionsUseCase.getTop250Collections(TOP_250_TV_SHOWS, PAGE)
            }.fold(
                onSuccess = {
                    _movieFamilyCollections.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    suspend fun isFilmExistsInCollectionSee(listFilms: List<EntityItemsDto>): List<Boolean> {
        val listBooleanIsFilmExists = mutableListOf<Boolean>()

        listFilms.forEach {
            listBooleanIsFilmExists.add(dbUseCase.getBooleanFromSeeCollection(it.kinopoiskId))
        }

        return listBooleanIsFilmExists.take(8)
    }

    companion object {
        private const val PAGE = 1
        const val COMICS_THEME = "COMICS_THEME"
        const val TOP_250 = "TOP_250_MOVIES"
        const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        const val VAMPIRE_THEME = "VAMPIRE_THEME"
        const val TOP_250_TV_SHOWS = "TOP_250_TV_SHOWS"
    }

}