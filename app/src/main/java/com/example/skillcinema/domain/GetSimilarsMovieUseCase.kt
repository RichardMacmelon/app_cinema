package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityFilmDto
import com.example.skillcinema.data.EntitySimilarsFilmsDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetSimilarsMovieUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getSimilarsMovie(id: Int?) : EntitySimilarsFilmsDto {
        return mainRepository.getSimilarsMovie(id)
    }
}