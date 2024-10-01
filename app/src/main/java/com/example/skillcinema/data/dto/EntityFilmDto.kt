package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityFilm
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityFilmDto(
    @Json(name = "kinopoiskId") override val kinopoiskId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "coverUrl") override val coverUrl: String?,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "year") override val year: Int?,
    @Json(name = "filmLength") override val filmLength: Int?,
    @Json(name = "logoUrl") override val logoUrl: String?,
    @Json(name = "ratingAgeLimits") override val ratingAgeLimits: String?,
    @Json(name = "countries") override val countries: List<EntityCountryDto>,
    @Json(name = "genres") override val genres: List<EntityGenreDto>,
    @Json(name = "description") override val description: String?,
    @Json(name = "shortDescription") override val shortDescription: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "webUrl") override val webUrl: String?
) : EntityFilm