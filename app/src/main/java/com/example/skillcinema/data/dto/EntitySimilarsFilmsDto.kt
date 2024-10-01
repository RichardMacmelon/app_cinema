package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityItemsSimilarsFilms
import com.example.skillcinema.entity.entityForApi.EntitySimilarsFilms
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntitySimilarsFilmsDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "items") override val items: List<EntityItemsSimilarsFilmsDto>
) : EntitySimilarsFilms

@JsonClass(generateAdapter = true)
data class EntityItemsSimilarsFilmsDto(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameRu") override val nameRu: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String
): EntityItemsSimilarsFilms