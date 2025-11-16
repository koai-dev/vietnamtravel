package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DestinationResponse(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: String?,
    val images: List<String>,
    val parentId: Long?,
    val children: List<DestinationResponse> = emptyList()
)
