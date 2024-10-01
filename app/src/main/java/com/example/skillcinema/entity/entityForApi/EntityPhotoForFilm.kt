package com.example.skillcinema.entity.entityForApi

interface EntityPhotoForFilm {
    val total : Int
    val totalPages: Int
    val items : List<EntityItemsPhoto>
}

interface EntityItemsPhoto {
    val imageUrl: String
    val previewUrl: String
}