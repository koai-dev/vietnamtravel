package com.travel.domain.model

data class Tour(
    val id: Long = 0,
    val titleVi: String,
    val titleEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val price: Double,
    val durationHours: Int,
    val destinationId: Long,
    val images: List<String>,
)
