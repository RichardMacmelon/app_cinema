package com.example.skillcinema.data

import com.example.skillcinema.entity.EntityFilm
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityFilmDto(
    @Json(name = "kinopoiskId") override val kinopoiskId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "year") override val year: Int?,
    @Json(name = "filmLength") override val filmLength: Int?
) : EntityFilm