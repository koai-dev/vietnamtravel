package com.travel.data.model

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class HotelRequest(
    val nameVi: String,
    val nameEn: String,
    var slug: String,
    val descriptionVi: String? = null,
    val descriptionEn: String? = null,
    val address: String? = null,
    val city: String? = null,
    val latitude: Double?= null,
    val longitude: Double? = null,
    val addressLink: String? = null,
    val contact: ContactInfoRequest? = null,
    val images: List<String>? = null,
    val tempUrlMap: Map<String, String>? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val amenities: List<String>? = null,
    val checkInTime: String? = null,
    val checkOutTime: String? = null,
    val cancellationPolicy: String? = null,
    val childPolicy: String? = null,
    val petPolicy: String? = null,
    val tags: List<String>? = null,
    val externalBookingLinks: List<String>? = null,
    val hostId: Long? = null,
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
    val phone: String? = null,
    val email: String? = null,
    val website: String? = null,
    val facebook: String? = null,
    val zalo: String? = null,
    val whatsapp: String? = null,
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
