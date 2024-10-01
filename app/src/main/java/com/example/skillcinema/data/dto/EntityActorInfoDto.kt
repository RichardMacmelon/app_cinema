package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityActorInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityActorInfoDto(
    @Json(name = "personId") override val personId: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "profession") override val profession: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "sex") override val sex: String,
    @Json(name = "films") override val films: List<EntityFilmographyDto>
): EntityActorInfo