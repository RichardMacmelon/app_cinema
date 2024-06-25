package com.example.skillcinema.presentation.tabBar.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.EntityFilmDto
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.domain.GetCollectionsUseCase
import com.example.skillcinema.domain.GetFilmInfoUseCase
import com.example.skillcinema.domain.GetPremieresUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPremieresUseCase: GetPremieresUseCase,
    private val getFilmInfoUseCase: GetFilmInfoUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    private var i = 1
    private val _moviePremiers = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val moviePremiers = _moviePremiers.asStateFlow()

    private val _movieTop250Collections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieTop250Collections = _movieTop250Collections.asStateFlow()

    private val _moviePopularCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val moviePopularCollections = _moviePopularCollections.asStateFlow()

    private val _movieVampireCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieVampireCollections = _movieVampireCollections.asStateFlow()

    private val _movieFamilyCollections = MutableStateFlow<List<EntityItemsDto>>(emptyList())
    val movieFamilyCollections = _movieFamilyCollections.asStateFlow()

    private val _infoFilm = MutableStateFlow<List<EntityFilmDto>>(emptyList())
    val infoFilm = _infoFilm.asStateFlow()

//    val pagedMovie : Flow<PagingData<EntityItemsDto>> = Pager(
//        config = PagingConfig(pageSize = 1),
//        initialKey = null,
//        pagingSourceFactory = { MyCollectionsPiginSource(getCollectionsUseCase) }
//    ).flow.cachedIn(viewModelScope)

    init {
        loadPremieres()
        loadCollectionsTop250()
        loadCollectionsPopular()
        loadCollectionsVampire()
        loadCollectionsFamily()
    }

    private fun loadPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getPremieresUseCase.getPremieres(2024, "JUNE")
            }.fold(
                onSuccess = {
                    _moviePremiers.value = it
                    // Работает, но тратит много запрсов
                    //                    val listInfoFilm: MutableList<EntityFilmDto> =
                    //                        emptyList<EntityFilmDto>().toMutableList()
                    //                    for (i in 0 until it.size) {
                    //                        val film = getFilmInfoUseCase.getInfoFilm(it.getOrNull(i)?.kinopoiskId)
                    //                        listInfoFilm.add(film)
                    //                        _infoFilm.value = listInfoFilm
                    //                    }
                },
                onFailure = { Log.d("MovieList viewModel loadPremieres", it.message ?: "") }
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
                    println(it)
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
                    println(it)
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
                    println(it)
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
                    println(it)
                },
                onFailure = { Log.d("MovieList viewModel loadCollections", it.message ?: "") }
            )
        }
    }

    companion object {
        private const val PAGE = 1
        private val TOP_250 = "TOP_250_MOVIES"
        private val TOP_POPULAR_ALL = "TOP_POPULAR_ALL"
        private val VAMPIRE_THEME = "VAMPIRE_THEME"
        private val FAMILY = "FAMILY"
    }

}