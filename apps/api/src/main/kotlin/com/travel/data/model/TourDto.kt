package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TourResponse(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val durationHours: Int,
    val destinationId: Long,
    val images: List<String>,
)
