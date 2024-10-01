package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityPeople
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityPeopleDto(
    @Json(name = "staffId") override val staffId: Int,
    @Json(name = "nameRu") override val nameRu: String,
    @Json(name = "nameEn") override val nameEn: String,
    @Json(name = "description") override val description: String?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "professionText") override val professionText: String,
    @Json(name = "professionKey") override val professionKey: String
) : EntityPeople
