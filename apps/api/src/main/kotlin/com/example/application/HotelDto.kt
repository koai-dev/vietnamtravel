package com.example.application

import kotlinx.serialization.Serializable

@Serializable
data class HotelResponse(
    val id: Long,
    val name: String,
    val description: String,
    val address: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val hostId: Long?,
    val rating: Float,
    val reviewCount: Int
)
