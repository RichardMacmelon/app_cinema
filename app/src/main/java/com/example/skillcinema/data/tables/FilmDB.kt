package com.example.skillcinema.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.entity.entityForDB.EntityFilmDB

@Entity(tableName = "film_db")
data class FilmDB (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Int= 0,
    @ColumnInfo(name = "collection_id")
    override val collectionId: Int,
    @ColumnInfo(name = "film_id")
    override val filmId: Int,
    @ColumnInfo(name = "film_name")
    override val filmName: String,
    @ColumnInfo(name = "film_genre")
    override val filmGenre: String,
    @ColumnInfo(name = "film_poster")
    override val filmPoster: String,
    @ColumnInfo(name = "film_rating")
    override val filmRating: String,
    @ColumnInfo(name = "film_year")
    override val filmYear: String
) : EntityFilmDB