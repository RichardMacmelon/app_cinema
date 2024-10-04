package com.example.skillcinema.entity.entityForDB

interface EntityFilmDB {
    val id: Int
    val collectionId: Int
    val filmId: Int
    val filmName: String
    val filmGenre: String
    val filmPoster: String
    val filmRating: String
    val filmYear : String
}