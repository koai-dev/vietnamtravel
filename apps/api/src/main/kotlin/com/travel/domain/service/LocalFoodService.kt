package com.travel.domain.service

import com.google.gson.Gson
import com.travel.domain.model.LocalFood
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.LocalFoodRepository

class LocalFoodService(
    private val localFoodRepository: LocalFoodRepository,
    private val destinationRepository: DestinationRepository,
    private val imageMappingService: ImageMappingService,
) {
    suspend fun create(localFood: LocalFood): LocalFood {
        validateDestination(localFood.destinationId)
        val resolvedImages =
            imageMappingService.resolveImages(
                Gson().toJson(localFood.images),
                localFood.tempUrlMap ?: emptyMap(),
            )
        val localFoodWithResolvedImages = localFood.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)
        return localFoodRepository.create(localFoodWithResolvedImages)
    }

    suspend fun update(
        id: Long,
        localFood: LocalFood,
    ): LocalFood? {
        validateDestination(localFood.destinationId)
        val resolvedImages =
            imageMappingService.resolveImages(
                Gson().toJson(localFood.images),
                localFood.tempUrlMap ?: emptyMap(),
            )
        val localFoodWithResolvedImages = localFood.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)
        return localFoodRepository.update(id, localFoodWithResolvedImages)
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
