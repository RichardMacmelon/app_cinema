package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntitySearchDataMovie
import com.example.skillcinema.entity.entityForApi.EntitySearchGenresMovie
import com.example.skillcinema.entity.entityForApi.EntitySearchMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntitySearchMovieDto(
    @Json(name = "keyword") override val keyword: String,
    @Json(name = "films") override val films: List<EntitySearchDataMovieDto>
) : EntitySearchMovie

@JsonClass(generateAdapter = true)
data class EntitySearchDataMovieDto(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "year") override val year: String?,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "genres") override val genres: List<EntitySearchGenresMovieDto>
) : EntitySearchDataMovie

@JsonClass(generateAdapter = true)
data class EntitySearchGenresMovieDto(
    @Json(name = "genre") override val genre: String
) : EntitySearchGenresMovie