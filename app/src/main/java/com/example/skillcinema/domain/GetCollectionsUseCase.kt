package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getTop250Collections(type: String, page: Int): List<EntityItemsDto> {
        return mainRepository.getMovieListCollections(type, page)
    }

}