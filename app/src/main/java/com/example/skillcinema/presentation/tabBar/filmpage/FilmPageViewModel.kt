package com.example.skillcinema.presentation.tabBar.filmpage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillcinema.data.EntityFilmDto
import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.data.EntityItemsPhotoDto
import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.data.EntityPhotoForFilmDto
import com.example.skillcinema.data.EntitySimilarsFilmsDto
import com.example.skillcinema.domain.GetActorForMovieUseCase
import com.example.skillcinema.domain.GetCollectionsUseCase
import com.example.skillcinema.domain.GetFilmInfoUseCase
import com.example.skillcinema.domain.GetPhotoForMovieUseCase
import com.example.skillcinema.domain.GetSimilarsMovieUseCase
import com.example.skillcinema.entity.EntityPhotoForFilm
import com.example.skillcinema.presentation.tabBar.AllMoviePage.MyCollectionsPiginSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    private val getFilmInfoUseCase: GetFilmInfoUseCase,
    private val getSimilarsMovieUseCase: GetSimilarsMovieUseCase,
    private val getActorForMovieUseCase: GetActorForMovieUseCase,
    private val getPhotoForMovieUseCase: GetPhotoForMovieUseCase
) : ViewModel() {

    private val _infoMovie = MutableSharedFlow<EntityFilmDto>()
    val infoMovie = _infoMovie.asSharedFlow()

    private val _similarsMovie = MutableSharedFlow<EntitySimilarsFilmsDto>()
    val similarsMovie = _similarsMovie.asSharedFlow()

    private val _actorInfo = MutableStateFlow<List<EntityPeopleDto>>(emptyList())
    val actorInfo = _actorInfo.asStateFlow()

    private val _workerMovieInfo = MutableStateFlow<List<EntityPeopleDto>>(emptyList())
    val workerMovieInfo = _workerMovieInfo.asStateFlow()

    private val _photoMovie = MutableSharedFlow<EntityPhotoForFilmDto>()
    val photoMovie = _photoMovie.asSharedFlow()

    fun loadPhotoForMovie(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getPhotoForMovieUseCase.getPhotoForMovieUseCase(id, "SCREENSHOT", 1)
            }.fold(
                onSuccess = {_photoMovie.emit(it)},
                onFailure = { Log.d("FilmPageViewModelPhoto", it.message ?: "") }
            )
        }
    }

    fun loadInfoPeople(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getActorForMovieUseCase.getPeopleForMovieUseCase(filmId)
            }.fold(
                onSuccess = {
                    val listActor = emptyList<EntityPeopleDto>().toMutableList()
                    val listWorker = emptyList<EntityPeopleDto>().toMutableList()
                    for (i in 0 until it.size) {
                        if (it[i].professionKey == "ACTOR") {
                            listActor.add(it[i])
                        } else {
                            listWorker.add(it[i])
                        }
                    }
                    _actorInfo.value = listActor
                    _workerMovieInfo.value = listWorker
                },
                onFailure = { Log.d("FilmPageViewModelActor", it.message ?: "") }
            )
        }
    }

    fun loadInfoMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getFilmInfoUseCase.getInfoFilm(id)
            }.fold(
                onSuccess = { _infoMovie.emit(it) },
                onFailure = { Log.d("FilmPageViewModel", it.message ?: "") }
            )
        }
    }

    fun loadSimilarsMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getSimilarsMovieUseCase.getSimilarsMovie(id)
            }.fold(
                onSuccess = {
                    _similarsMovie.emit(it)
                },
                onFailure = { Log.d("FilmPageViewModel", it.message ?: "") }
            )
        }
    }

}