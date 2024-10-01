package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityFilmDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetFilmInfoUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getInfoFilm(id: Int?) : EntityFilmDto {
        return apiRepository.getFilmInformation(id)
    }
}