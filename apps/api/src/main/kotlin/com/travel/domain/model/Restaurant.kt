package com.travel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: Long,
    val name: String,
    val description: String,
    val images: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val destinationId: Long,
    val localFoods: List<LocalFood> = emptyList(),
)
