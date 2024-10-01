package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityPeopleDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetActorForMovieUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {
    suspend fun getPeopleForMovieUseCase(filmId: Int) : List<EntityPeopleDto> {
        return apiRepository.getPeopleForMovie(filmId)
    }
}