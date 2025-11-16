package com.travel.domain.service

import com.travel.domain.model.LocalFood
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.LocalFoodRepository

class LocalFoodService(
    private val localFoodRepository: LocalFoodRepository,
    private val destinationRepository: DestinationRepository
) {

    suspend fun create(localFood: LocalFood): LocalFood {
        validateDestination(localFood.destinationId)
        return localFoodRepository.create(localFood)
    }

    suspend fun update(id: Long, localFood: LocalFood): LocalFood? {
        validateDestination(localFood.destinationId)
        return localFoodRepository.update(id, localFood)
    }

    suspend fun delete(id: Long) {
        localFoodRepository.delete(id)
    }

    suspend fun getById(id: Long): LocalFood? {
        return localFoodRepository.getById(id)
    }

    suspend fun listByDestinationId(destinationId: Long): List<LocalFood> {
        return localFoodRepository.listByDestinationId(destinationId)
    }

    private suspend fun validateDestination(destinationId: Long) {
        destinationRepository.findById(destinationId) ?: throw IllegalArgumentException("Destination with id $destinationId not found")
    }
}
