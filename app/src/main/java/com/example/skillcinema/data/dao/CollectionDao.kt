package com.example.skillcinema.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skillcinema.data.tables.CollectionDB
import com.example.skillcinema.data.tables.CollectionWithFilmsDB
import com.example.skillcinema.data.tables.FilmDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Query("SELECT * FROM collection")
    fun getAll(): Flow<List<CollectionWithFilmsDB>>

    @Query("SELECT * FROM collection")
    fun getCollection(): Flow<List<CollectionDB>>

    @Query("SELECT * FROM film_db")
    fun getAllFilm(): Flow<List<FilmDB>>

    @Query("SELECT * FROM film_db WHERE collection_id = :collectionId")
    fun getFilmsFlowByCollectionId(collectionId: Int): Flow<List<FilmDB>>

    @Query("SELECT * FROM film_db WHERE collection_id = :collectionId")
    suspend fun getFilmsByCollectionId(collectionId: Int): List<FilmDB>

    @Insert
    suspend fun insertCollection(collection: CollectionDB)

    @Insert
    suspend fun insertFilm(film: FilmDB)

    @Query("UPDATE Collection SET collection_size = :newSize WHERE collection_id = :collectionId")
    suspend fun updateCollectionSize(collectionId: Int, newSize: Int)

    @Query("DELETE FROM collection WHERE collection_id = :collectionId")
    suspend fun deleteCollectionById(collectionId: Int): Int

    @Query("DELETE FROM film_db WHERE collection_id = :collectionId")
    suspend fun deleteAllFilmsInCollectionId(collectionId: Int)

    @Query(
        """
        DELETE FROM film_db
        WHERE collection_id = :collectionId
        AND film_id = :filmId
        AND film_name = :filmName
        AND film_genre = :filmGenre
        AND film_poster = :filmPoster
        AND film_rating = :filmRating
    """
    )
    suspend fun deleteFilm(
        collectionId: Int,
        filmId: Int,
        filmName: String,
        filmGenre: String,
        filmPoster: String,
        filmRating: String
    )

    @Query("SELECT EXISTS(SELECT 1 FROM collection WHERE collection_id = :collectionId)")
    suspend fun isCollectionExists(collectionId: Int): Boolean

    @Query("SELECT COUNT(*) > 0 FROM film_db WHERE collection_id = :collectionId AND film_id = :filmId")
    suspend fun isFilmInCollection(collectionId: Int, filmId: Int): Boolean

    @Query(
        """
        SELECT EXISTS (
            SELECT 1 FROM film_db
            WHERE collection_id = :collectionId 
            AND film_id = :filmId 
            AND film_name = :filmName 
            AND film_genre = :filmGenre 
            AND film_poster = :filmPoster 
            AND film_rating = :filmRating
        )
    """
    )
    suspend fun isFilmExists(
        collectionId: Int,
        filmId: Int,
        filmName: String,
        filmGenre: String,
        filmPoster: String,
        filmRating: String
    ): Boolean

    @Query("SELECT COUNT(*) FROM film_db WHERE collection_id = :collectionId")
    suspend fun getFilmCountByCollectionId(collectionId: Int): Int

    @Query("SELECT DISTINCT collection_id FROM collection")
    suspend fun getAllCollectionIds(): List<Int>

    @Query("SELECT collection_name FROM collection WHERE collection_id = :collectionId")
    suspend fun getCollectionNameById(collectionId: Int): String

}
