package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityItemsDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetPremieresUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getPremieres(year: Int, month: String) : List<EntityItemsDto> {
        return mainRepository.getPremieres(year, month)
    }
}