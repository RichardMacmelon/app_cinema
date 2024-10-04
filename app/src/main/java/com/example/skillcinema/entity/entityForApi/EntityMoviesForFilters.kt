package com.example.skillcinema.entity.entityForApi

interface EntityMoviesForFilters {
    val total: Int
    val totalPages: Int
    val items : List<EntityItemsMoviesForFilters>
}

interface EntityItemsMoviesForFilters {
    val kinopoiskId : Int
    val nameRu : String?
    val nameEn : String?
    val nameOriginal : String?
    val genres: List<EntityGenresItemsMoviesForFilters>
    val ratingKinopoisk: Double?
    val ratingImdb : Double?
    val posterUrlPreview: String
}

interface EntityGenresItemsMoviesForFilters {
    val genre : String?
}