package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.dto.EntitySimilarsFilmsDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetSimilarsMovieUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getSimilarsMovie(id: Int?) : EntitySimilarsFilmsDto {
        return apiRepository.getSimilarsMovie(id)
    }
}