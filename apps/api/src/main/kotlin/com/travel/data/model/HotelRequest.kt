package com.travel.data.model

import io.ktor.server.plugins.requestvalidation.ValidationResult
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
    val tempUrlMap: Map<String, String>? = null,
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
    val hostId: Long?,
)

fun HotelRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (nameVi.isBlank()) {
        errors.add("Vietnamese name cannot be blank.")
    }
    if (nameEn.isBlank()) {
        errors.add("English name cannot be blank.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class ContactInfoRequest(
    val phone: String?,
    val email: String?,
    val website: String?,
    val facebook: String?,
    val zalo: String?,
    val whatsapp: String?,
)

@Serializable
data class RatingUpdateRequest(
    val rating: Float,
)

fun RatingUpdateRequest.validate(): ValidationResult {
    return if (rating in 0.0..5.0) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid("Rating must be between 0.0 and 5.0.")
    }
}
