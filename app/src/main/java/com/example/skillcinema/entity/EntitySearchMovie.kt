package com.example.skillcinema.entity

interface EntitySearchMovie {
    val keyword: String
    val films: List<EntitySearchDataMovie>
}

interface EntitySearchDataMovie {
    val filmId: Int
    val nameRu: String?
    val nameEn: String?
    val year: String?
    val rating: String?
    val posterUrlPreview: String?
    val genres : List<EntitySearchGenresMovie>
}

interface EntitySearchGenresMovie {
    val genre: String
}