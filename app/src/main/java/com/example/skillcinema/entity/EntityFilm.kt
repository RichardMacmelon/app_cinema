package com.example.skillcinema.entity

interface EntityFilm {
    val kinopoiskId: Int
    val nameRu: String?
    val posterUrl: String
    val ratingKinopoisk: Double?
    val year: Int?
    val filmLength: Int?
}