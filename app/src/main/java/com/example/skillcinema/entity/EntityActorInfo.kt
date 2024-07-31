package com.example.skillcinema.entity

interface EntityActorInfo {
    val personId: Int
    val nameRu: String?
    val nameEn: String?
    val profession: String?
    val posterUrl: String?
    val sex: String
    val films: List<EntityFilmography>
}
