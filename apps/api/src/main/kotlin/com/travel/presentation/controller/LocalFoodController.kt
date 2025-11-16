package com.travel.presentation.controller

import com.travel.data.model.LocalFoodRequest
import com.travel.data.model.LocalFoodResponse
import com.travel.domain.model.LocalFood
import com.travel.domain.service.LocalFoodService

class LocalFoodController(private val localFoodService: LocalFoodService) {

    suspend fun create(request: LocalFoodRequest): LocalFoodResponse {
        val localFood = LocalFood(
            id = 0,
            destinationId = request.destinationId,
            nameVi = request.nameVi,
            nameEn = request.nameEn,
            descriptionVi = request.descriptionVi,
            descriptionEn = request.descriptionEn,
            images = request.images
        )
        return localFoodService.create(localFood).toResponse()
    }

    suspend fun update(id: Long, request: LocalFoodRequest): LocalFoodResponse? {
        val localFood = LocalFood(
            id = id,
            destinationId = request.destinationId,
            nameVi = request.nameVi,
            nameEn = request.nameEn,
            descriptionVi = request.descriptionVi,
            descriptionEn = request.descriptionEn,
            images = request.images
        )
        return localFoodService.update(id, localFood)?.toResponse()
    }

    suspend fun delete(id: Long) {
        localFoodService.delete(id)
    }

    suspend fun getById(id: Long): LocalFoodResponse? {
        return localFoodService.getById(id)?.toResponse()
    }

    suspend fun listByDestinationId(destinationId: Long): List<LocalFoodResponse> {
        return localFoodService.listByDestinationId(destinationId).map { it.toResponse() }
    }

    private fun LocalFood.toResponse(): LocalFoodResponse {
        return LocalFoodResponse(
            id = id,
            destinationId = destinationId,
            nameVi = nameVi,
            nameEn = nameEn,
            descriptionVi = descriptionVi,
            descriptionEn = descriptionEn,
            images = images
        )
    }
}
