package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityIdCountryAndGenresDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetIdForCountryAndGenreUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend fun getIdForCountryAndGenre() : EntityIdCountryAndGenresDto {
        return apiRepository.getIdForCountryAndGenre()
    }
}