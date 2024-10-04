package com.example.skillcinema.data.dto

import com.example.skillcinema.entity.entityForApi.EntityIdCountries
import com.example.skillcinema.entity.entityForApi.EntityIdCountryAndGenres
import com.example.skillcinema.entity.entityForApi.EntityIdGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntityIdCountryAndGenresDto(
    @Json(name = "genres") override val genres: List<EntityIdGenresDto>,
    @Json(name = "countries") override val countries: List<EntityIdCountriesDto>
) : EntityIdCountryAndGenres

@JsonClass(generateAdapter = true)
data class EntityIdGenresDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "genre") override val genre: String
) : EntityIdGenres

@JsonClass(generateAdapter = true)
data class EntityIdCountriesDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "country") override val country: String
) : EntityIdCountries