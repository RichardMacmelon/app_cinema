package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityGenresItemsMoviesForFilters
import com.example.skillcinema.entity.entityForApi.EntityItemsMoviesForFilters
import com.example.skillcinema.entity.entityForApi.EntityMoviesForFilters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityMoviesForFiltersDto(
    @Json(name = "total") override val total: Int,
    @Json(name = "totalPages") override val totalPages: Int,
    @Json(name = "items") override val items: List<EntityItemsMoviesForFiltersDto>
) : EntityMoviesForFilters

@JsonClass(generateAdapter = true)
data class EntityItemsMoviesForFiltersDto(
    @Json(name = "kinopoiskId") override val kinopoiskId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "genres") override val genres: List<EntityGenresItemsMoviesForFiltersDto>,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "ratingImdb") override val ratingImdb: Double?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String
) : EntityItemsMoviesForFilters

@JsonClass(generateAdapter = true)
data class EntityGenresItemsMoviesForFiltersDto(
    @Json(name = "genre") override val genre: String?
) : EntityGenresItemsMoviesForFilters