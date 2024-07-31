package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityPhotoForFilmDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetPhotoForMovieUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getPhotoForMovieUseCase(id: Int?, type: String, page: Int): EntityPhotoForFilmDto {
        return mainRepository.getPhotoForMovie(id, type, page)
    }
}