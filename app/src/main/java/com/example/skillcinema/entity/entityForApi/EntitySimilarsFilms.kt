package com.example.skillcinema.entity.entityForApi

interface EntitySimilarsFilms {
    val total: Int
    val items: List<EntityItemsSimilarsFilms>
}

interface EntityItemsSimilarsFilms {
    val filmId: Int
    val nameRu: String
    val posterUrlPreview: String
}