package com.example.skillcinema.entity

interface EntityCollectionsMovies {
    val total: Int
    val items: List<EntityItems>
}

interface EntityItems {
    val kinopoiskId: Int
    val nameRu: String?
    val nameEn: String?
    val nameOriginal: String?
    val countries: List<EntityCountry>
    val genres : List<EntityGenre>
    val ratingKinopoisk: Double?
    val ratingImbd: Double?
    val year: String?
    val type: String?
    val posterUrl: String?
    val posterUrlPreview : String?
}

interface EntityCountry {
    val country: String
}

interface EntityGenre {
    val genre: String
}
