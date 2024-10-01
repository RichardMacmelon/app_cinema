package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.dto.EntityItemsDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getTop250Collections(type: String, page: Int): List<EntityItemsDto> {
        return apiRepository.getMovieListCollections(type, page)
    }

}