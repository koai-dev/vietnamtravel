package com.travel.data.model

import com.travel.domain.model.LocalFood
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRequest(
    val name: String,
    val description: String,
    val images: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val destinationId: Long,
    val localFoodIds: List<Long>,
)

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
