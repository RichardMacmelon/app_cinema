package com.example.skillcinema.domain

import com.example.skillcinema.data.EntityActorInfoDto
import com.example.skillcinema.data.MainRepository
import javax.inject.Inject

class GetInfoForPersonUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend fun getInfoForPerson(id: Int?): EntityActorInfoDto {
        return mainRepository.getInfoForPerson(id)
    }
}