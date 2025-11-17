package com.travel.domain.model

data class Hotel(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val address: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val hostId: Long?,
    val rating: Float,
    val reviewCount: Int,
)
