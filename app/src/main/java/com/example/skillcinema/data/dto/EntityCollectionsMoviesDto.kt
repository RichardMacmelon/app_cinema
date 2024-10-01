package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityCollectionsMovies
import com.example.skillcinema.entity.entityForApi.EntityCountry
import com.example.skillcinema.entity.entityForApi.EntityGenre
import com.example.skillcinema.entity.entityForApi.EntityItems
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityCollectionsMoviesDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "items") override val items: List<EntityItemsDto>
) : EntityCollectionsMovies

@JsonClass(generateAdapter = true)
data class EntityItemsDto(
    @Json(name = "kinopoiskId") override val kinopoiskId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "countries") override val countries: List<EntityCountryDto>,
    @Json(name = "genres") override val genres: List<EntityGenreDto>,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "ratingImbd") override val ratingImbd: Double?,
    @Json(name = "year") override val year: String?,
    @Json(name = "type") override val type: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String
) : EntityItems

@JsonClass(generateAdapter = true)
data class EntityCountryDto(
    @Json(name = "country") override val country: String
) : EntityCountry

@JsonClass(generateAdapter = true)
data class EntityGenreDto(
    @Json(name = "genre") override val genre: String
) : EntityGenre
