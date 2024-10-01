package com.example.skillcinema.presentation.tabBar.filmpage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.dto.EntityFilmDto
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.data.dto.EntityPhotoForFilmDto
import com.example.skillcinema.data.dto.EntitySimilarsFilmsDto
import com.example.skillcinema.data.tables.FilmDB
import com.example.skillcinema.domain.dbUseCase.DBUseCase
import com.example.skillcinema.domain.useCase.GetActorForMovieUseCase
import com.example.skillcinema.domain.useCase.GetCollectionsUseCase
import com.example.skillcinema.domain.useCase.GetFilmInfoUseCase
import com.example.skillcinema.domain.useCase.GetPhotoForMovieUseCase
import com.example.skillcinema.domain.useCase.GetSimilarsMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    private val getFilmInfoUseCase: GetFilmInfoUseCase,
    private val getSimilarsMovieUseCase: GetSimilarsMovieUseCase,
    private val getActorForMovieUseCase: GetActorForMovieUseCase,
    private val getPhotoForMovieUseCase: GetPhotoForMovieUseCase,
    private val dbUseCase: DBUseCase
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

    suspend fun checkFilmIntoCollection(film: FilmDB): Boolean {
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

    suspend fun buttonState(collectionId: Int, film: FilmDB): Boolean {
        val filmWithNewCollectionId = FilmDB(
            collectionId = collectionId,
            filmId = film.filmId,
            filmName = film.filmName,
            filmGenre = film.filmGenre,
            filmPoster = film.filmPoster,
            filmRating = film.filmRating,
            filmYear = film.filmYear
        )
        return checkFilmIntoCollection(filmWithNewCollectionId)
    }

    fun loadPhotoForMovie(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getPhotoForMovieUseCase.getPhotoForMovieUseCase(id, "SCREENSHOT", 1)
            }.fold(
                onSuccess = { _photoMovie.emit(it) },
                onFailure = { Log.d("FilmPageViewModelPhoto", it.message ?: "") }
            )
        }
    }

    fun processMovieData(movie: EntityFilmDto): MovieUIModel {
        val coverUrl = movie.coverUrl
        val logoUrl = movie.logoUrl
        val posterUrl = movie.posterUrl.toString()

        val ratingKinopoisk = movie.ratingKinopoisk?.toString() ?: ""

        val name = movie.nameRu ?: movie.nameEn ?: ""

        val year = movie.year?.let { "$it," } ?: ""

        val genres = movie.genres.joinToString { it.genre }
        val countries = "${movie.countries.joinToString { it.country }},"

        val filmLength = if (movie.filmLength == null) {
            ""
        } else if (movie.filmLength > 60) {
            val hours = movie.filmLength / 60
            val minutes = movie.filmLength % 60
            "$hours ч $minutes мин,"
        } else {
            "${movie.filmLength} мин,"
        }

        val ratingAgeLimits = movie.ratingAgeLimits?.replace("age", "")?.plus("+") ?: ""

        return MovieUIModel(
            coverUrl = coverUrl,
            logoUrl = logoUrl,
            posterUrl = posterUrl,
            ratingKinopoisk = ratingKinopoisk,
            name = name,
            year = year,
            genres = genres,
            countries = countries,
            filmLength = filmLength,
            ratingAgeLimits = ratingAgeLimits,
            shortDescription = movie.shortDescription,
            description = movie.description,
            webUrl = movie.webUrl
        )
    }

    fun loadInfoPeople(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getActorForMovieUseCase.getPeopleForMovieUseCase(filmId)
            }.fold(
                onSuccess = {
                    val listActor = emptyList<EntityPeopleDto>().toMutableList()
                    val listWorker = emptyList<EntityPeopleDto>().toMutableList()
                    for (i in it.indices) {
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