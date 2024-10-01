package com.example.skillcinema.domain.useCase

import com.example.skillcinema.data.repository.MainRepository
import com.example.skillcinema.data.dto.EntityActorInfoDto
import com.example.skillcinema.data.repository.ApiRepository
import javax.inject.Inject

class GetInfoForPersonUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend fun getInfoForPerson(id: Int?): EntityActorInfoDto {
        return apiRepository.getInfoForPerson(id)
    }
}