package com.example.skillcinema.entity.entityForApi

interface EntitySerial {
    val total: Int
    val items: List<EntitySerialItems>
}

interface EntitySerialItems {
    val number : Int
    val episodes: List<EntitySerialItemsEpisodes>
}

interface EntitySerialItemsEpisodes {
    val seasonNumber: Int
    val episodeNumber: Int
    val nameRuL: String?
    val nameEn: String?
    val synopsis: String?
    val releaseDate: String?
}