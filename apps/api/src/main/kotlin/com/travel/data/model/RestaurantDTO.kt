package com.travel.data.model

import com.travel.domain.model.LocalFood
import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequest(
    val name: String,
    val description: String,
    val images: List<String>,
    val tempUrlMap: Map<String, String>? = null,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val destinationId: Long,
    val localFoodIds: List<Long>,
)

fun RestaurantRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (name.isBlank()) {
        errors.add("Name cannot be blank.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class RestaurantResponse(
    val id: Long,
    val name: String,
    val description: String,
    val images: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val destinationId: Long,
    val localFoods: List<LocalFood>,
)
