package com.example.skillcinema.presentation.tabBar.filmpage

data class MovieUIModel(
    val coverUrl: String?,
    val logoUrl: String?,
    val posterUrl: String,
    val ratingKinopoisk: String,
    val name: String,
    val year: String,
    val genres: String,
    val countries: String,
    val filmLength: String,
    val ratingAgeLimits: String,
    val shortDescription: String?,
    val description: String?,
    val webUrl: String?
)