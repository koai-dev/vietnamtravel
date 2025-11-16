package com.travel.domain.model

data class LocalFood(
    val id: Long,
    val destinationId: Long,
    val nameVi: String,
    val nameEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val images: List<String>
)
