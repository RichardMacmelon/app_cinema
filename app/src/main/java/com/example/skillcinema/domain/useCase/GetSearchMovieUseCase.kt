package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntitySearchDataMovieDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getSearchMovie(keyword: String, page: Int) : List<EntitySearchDataMovieDto> {
        return apiRepository.getSearchMovie(keyword, page).films
    }
}