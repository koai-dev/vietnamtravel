package com.travel.presentation.controller

import com.travel.data.model.LocalFoodRequest
import com.travel.data.model.LocalFoodResponse
import com.travel.domain.model.LocalFood
import com.travel.domain.service.LocalFoodService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class LocalFoodController(private val localFoodService: LocalFoodService) : BaseController() {
    suspend fun create(call: ApplicationCall, request: LocalFoodRequest) {
        val localFood =
            LocalFood(
                id = 0,
                destinationId = request.destinationId,
                nameVi = request.nameVi,
                nameEn = request.nameEn,
                descriptionVi = request.descriptionVi,
                descriptionEn = request.descriptionEn,
                images = request.images,
            )
        val createdLocalFood = localFoodService.create(localFood).toResponse()
        respondWith(call, createdLocalFood)
    }

    suspend fun update(
        call: ApplicationCall,
        id: Long,
        request: LocalFoodRequest,
    ) {
        val localFood =
            LocalFood(
                id = id,
                destinationId = request.destinationId,
                nameVi = request.nameVi,
                nameEn = request.nameEn,
                descriptionVi = request.descriptionVi,
                descriptionEn = request.descriptionEn,
                images = request.images,
            )
        val updatedLocalFood = localFoodService.update(id, localFood)?.toResponse()
        if (updatedLocalFood != null) {
            respondWith(call, updatedLocalFood)
        } else {
            respondWithError(call, "Local Food not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun delete(call: ApplicationCall, id: Long) {
        localFoodService.delete(id)
        respondWith(call, true, "Local Food deleted successfully")
    }

    suspend fun getById(call: ApplicationCall, id: Long) {
        val localFood = localFoodService.getById(id)?.toResponse()
        if (localFood != null) {
            respondWith(call, localFood)
        } else {
            respondWithError(call, "Local Food not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun listByDestinationId(call: ApplicationCall, destinationId: Long) {
        val localFoods = localFoodService.listByDestinationId(destinationId).map { it.toResponse() }
        respondWith(call, localFoods)
    }

    private fun LocalFood.toResponse(): LocalFoodResponse {
        return LocalFoodResponse(
            id = id,
            destinationId = destinationId,
            nameVi = nameVi,
            nameEn = nameEn,
            descriptionVi = descriptionVi,
            descriptionEn = descriptionEn,
            images = images,
        )
    }
}
