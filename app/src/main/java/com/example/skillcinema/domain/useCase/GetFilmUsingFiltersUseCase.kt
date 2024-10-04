package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityMoviesForFiltersDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetFilmUsingFiltersUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getFilmUsingFilters(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        page: Int
    ): EntityMoviesForFiltersDto {
        return apiRepository.getFilmUsingFilters(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            page
        )
    }
}