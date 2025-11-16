package com.travel.presentation.request

import kotlinx.serialization.Serializable

@Serializable
data class HotelRequest(
    val nameVi: String,
    val nameEn: String,
    var slug: String,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val address: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val addressLink: String?,
    val contact: ContactInfoRequest?,
    val images: List<String>?,
    val minPrice: Double?,
    val maxPrice: Double?,
    val amenities: List<String>?,
    val checkInTime: String?,
    val checkOutTime: String?,
    val cancellationPolicy: String?,
    val childPolicy: String?,
    val petPolicy: String?,
    val tags: List<String>?,
    val externalBookingLinks: List<String>?,
    val hostId: Long?
)

@Serializable
data class ContactInfoRequest(
    val phone: String?,
    val email: String?,
    val website: String?,
    val facebook: String?,
    val zalo: String?,
    val whatsapp: String?
)

@Serializable
data class RatingUpdateRequest(
    val rating: Float
)