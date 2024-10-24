package com.example.skillcinema.domain.dbUseCase

import com.example.skillcinema.data.repository.DBRepository
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.CollectionWithFilmsDB
import com.example.skillcinema.data.tables.FilmDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun getAllInfo(): Flow<List<CollectionWithFilmsDB>> {
        return dbRepository.getAllInfo()
    }

    fun getAllCollection(): Flow<List<CollectionDB>> {
        return dbRepository.getAllCollection()
    }

    fun getAllFilms() : Flow<List<FilmDB>> {
        return dbRepository.getAllFilm()
    }

    fun getFilmsFlowByCollectionId(collectionId: Int): Flow<List<FilmDB>> {
        return dbRepository.getFilmsFlowByCollectionId(collectionId)
    }

    suspend fun getBooleanFromSeeCollection(filmId: Int) : Boolean {
        return dbRepository.getBooleanFromSeeCollection(filmId)
    }

    suspend fun getFilmsByCollectionId(collectionId: Int): List<FilmDB> {
        return dbRepository.getFilmsByCollectionId(collectionId)
    }

    suspend fun insertCollection(collectionDB: CollectionDB) {
        dbRepository.insertCollection(collectionDB)
    }

    suspend fun insertFilm(film: FilmDB) {
        dbRepository.insertFilm(film)
    }

    suspend fun deleteCollection(collectionId: Int) {
        dbRepository.deleteCollection(collectionId)
    }

    suspend fun deleteAllFilmsInCollectionId(collectionId: Int) {
        dbRepository.deleteAllFilmsInCollectionId(collectionId)
    }

    suspend fun deleteFilm(film: FilmDB) {
        dbRepository.deleteFilm(film)
    }

    suspend fun checkFilmIntoCollection(film: FilmDB): Boolean {
        return dbRepository.checkFilmIntoCollection(film)
    }

    suspend fun countForCollection(collectionId: Int) : Int {
        return dbRepository.countForCollection(collectionId)
    }

    suspend fun getListCollectionId() : List<Int>{
        return dbRepository.getListCollectionId()
    }

    suspend fun updateCollectionSize(collectionId: Int, newSize: Int) {
         dbRepository.updateCollectionSize(collectionId, newSize)
    }

    suspend fun checkCollection(collectionId: Int) : Boolean {
        return dbRepository.checkCollection(collectionId)
    }

    suspend fun getCollectionNameById(collectionId: Int) : String {
        return dbRepository.getCollectionNameById(collectionId)
    }

}