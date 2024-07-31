package com.example.skillcinema.presentation.tabBar.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.EntityFilmDto
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.domain.GetCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
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
                getCollectionsUseCase.getTop250Collections(POPULAR_SERIES, PAGE)
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
                getCollectionsUseCase.getTop250Collections(FAMILY, PAGE)
            }.fold(
                onSuccess = {
                    _movieFamilyCollections.value = it
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    companion object {
        private const val PAGE = 1
        private const val POPULAR_SERIES = "POPULAR_SERIES"
        private const val TOP_250 = "TOP_250_MOVIES"
        private const val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        private const val VAMPIRE_THEME = "VAMPIRE_THEME"
        private const val FAMILY = "FAMILY"
    }

}