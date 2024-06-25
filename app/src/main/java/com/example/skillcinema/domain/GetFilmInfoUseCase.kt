package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityFilmDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetFilmInfoUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getInfoFilm(id: Int?) : EntityFilmDto {
        return mainRepository.getFilmInformation(id)
    }
}