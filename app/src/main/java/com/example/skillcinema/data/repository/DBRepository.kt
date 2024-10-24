package com.example.skillcinema.data.repository

import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.CollectionWithFilmsDB
import com.example.skillcinema.data.tables.FilmDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepository @Inject constructor(
    private val mainRepository: MainRepository
) {

    fun getAllInfo(): Flow<List<CollectionWithFilmsDB>> {
        return mainRepository.getDBInfo().getAll()
    }

    fun getAllCollection(): Flow<List<CollectionDB>> {
        return mainRepository.getDBInfo().getCollection()
    }

    fun getAllFilm(): Flow<List<FilmDB>> {
        return mainRepository.getDBInfo().getAllFilm()
    }

    fun getFilmsFlowByCollectionId(collectionId: Int): Flow<List<FilmDB>> {
        return mainRepository.getDBInfo().getFilmsFlowByCollectionId(collectionId)
    }

    suspend fun getBooleanFromSeeCollection(filmId: Int) : Boolean {
        return mainRepository.getDBInfo().isFilmInCollection(1, filmId)
    }

    suspend fun getFilmsByCollectionId(collectionId: Int): List<FilmDB> {
        return mainRepository.getDBInfo().getFilmsByCollectionId(collectionId)
    }

    suspend fun insertCollection(collectionDB: CollectionDB) {
        mainRepository.getDBInfo().insertCollection(collectionDB)
    }

    suspend fun insertFilm(film: FilmDB) {
        mainRepository.getDBInfo().insertFilm(film)
    }

    suspend fun deleteCollection(collectionId: Int) {
        mainRepository.getDBInfo().deleteCollectionById(collectionId)
    }

    suspend fun deleteAllFilmsInCollectionId(collectionId: Int) {
        mainRepository.getDBInfo().deleteAllFilmsInCollectionId(collectionId)
    }

    suspend fun deleteFilm(film: FilmDB) {
        mainRepository.getDBInfo().deleteFilm(
            collectionId = film.collectionId,
            filmId = film.filmId,
            filmName = film.filmName,
            filmGenre = film.filmGenre,
            filmPoster = film.filmPoster,
            filmRating = film.filmRating
        )
    }

    suspend fun checkFilmIntoCollection(film: FilmDB): Boolean {
        return mainRepository.getDBInfo().isFilmExists(
            collectionId = film.collectionId,
            filmId = film.filmId,
            filmName = film.filmName,
            filmGenre = film.filmGenre,
            filmPoster = film.filmPoster,
            filmRating = film.filmRating
        )
    }

    suspend fun checkCollection(collectionId: Int) : Boolean {
        return mainRepository.getDBInfo().isCollectionExists(collectionId)
    }

    suspend fun getCollectionNameById(collectionId: Int) : String {
        return mainRepository.getDBInfo().getCollectionNameById(collectionId)
    }

    suspend fun countForCollection(collectionId: Int): Int {
        return mainRepository.getDBInfo().getFilmCountByCollectionId(collectionId)
    }

    suspend fun getListCollectionId(): List<Int> {
        return mainRepository.getDBInfo().getAllCollectionIds()
    }

    suspend fun updateCollectionSize(collectionId: Int, newSize: Int) {
        mainRepository.getDBInfo().updateCollectionSize(collectionId, newSize)
    }

}