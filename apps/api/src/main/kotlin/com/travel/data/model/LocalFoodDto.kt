package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocalFoodRequest(
    val destinationId: Long,
    val nameVi: String,
    val nameEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val images: List<String>,
)

@Serializable
data class LocalFoodResponse(
    val id: Long,
    val destinationId: Long,
    val nameVi: String,
    val nameEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val images: List<String>,
)
