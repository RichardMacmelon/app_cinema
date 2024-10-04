package com.example.skillcinema.entity.entityForApi

interface EntityIdCountryAndGenres {
    val genres: List<EntityIdGenres>
    val countries: List<EntityIdCountries>
}

interface EntityIdGenres {
    val id: Int
    val genre: String
}

interface EntityIdCountries {
    val id: Int
    val country: String
}