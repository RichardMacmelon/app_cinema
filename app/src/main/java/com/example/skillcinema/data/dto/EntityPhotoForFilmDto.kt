package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityItemsPhoto
import com.example.skillcinema.entity.entityForApi.EntityPhotoForFilm
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityPhotoForFilmDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "totalPages") override val totalPages: Int,
    @Json(name = "items") override val items: List<EntityItemsPhotoDto>
) : EntityPhotoForFilm

@JsonClass(generateAdapter = true)
data class EntityItemsPhotoDto(
    @Json(name = "previewUrl") override val previewUrl: String,
    @Json(name = "imageUrl") override val imageUrl: String
): EntityItemsPhoto
