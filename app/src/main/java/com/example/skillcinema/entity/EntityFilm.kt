package com.example.skillcinema.entity

interface EntityFilm {
    val kinopoiskId: Int
    val nameRu: String?
    val nameEn: String?
    val posterUrl: String?
    val coverUrl: String?
    val logoUrl: String?
    val ratingKinopoisk: Double?
    val ratingAgeLimits: String?
    val year: Int?
    val countries: List<EntityCountry>
    val genres: List<EntityGenre>
    val filmLength: Int?
    val description: String?
    val shortDescription: String?
    val webUrl: String?
}