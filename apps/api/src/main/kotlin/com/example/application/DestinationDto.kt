package com.example.application

import kotlinx.serialization.Serializable

@Serializable
data class DestinationResponse(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: String?,
    val images: List<String>
)
