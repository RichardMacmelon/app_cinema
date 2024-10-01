package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityFilmography
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityFilmographyDto(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "description") override val description: String?,
    @Json(name = "professionKey") override val professionKey: String
) : EntityFilmography
