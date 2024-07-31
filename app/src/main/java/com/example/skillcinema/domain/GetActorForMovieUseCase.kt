package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityPeopleDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetActorForMovieUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun getPeopleForMovieUseCase(filmId: Int) : List<EntityPeopleDto> {
        return mainRepository.getPeopleForMovie(filmId)
    }
}