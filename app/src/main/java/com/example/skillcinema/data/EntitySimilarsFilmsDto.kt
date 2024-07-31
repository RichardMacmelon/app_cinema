package com.example.skillcinema.data

import com.example.skillcinema.entity.EntityItemsSimilarsFilms
import com.example.skillcinema.entity.EntitySimilarsFilms
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