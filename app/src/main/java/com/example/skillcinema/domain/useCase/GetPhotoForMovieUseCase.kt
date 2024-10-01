package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityPhotoForFilmDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetPhotoForMovieUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getPhotoForMovieUseCase(id: Int?, type: String, page: Int): EntityPhotoForFilmDto {
        return apiRepository.getPhotoForMovie(id, type, page)
    }
}