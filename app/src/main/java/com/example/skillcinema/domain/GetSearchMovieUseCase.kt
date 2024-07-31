package com.example.skillcinema.domain

import com.example.skillcinema.data.EntitySearchDataMovieDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getSearchMovie(keyword: String, page: Int) : List<EntitySearchDataMovieDto> {
        return mainRepository.getSearchMovie(keyword, page).films
    }
}