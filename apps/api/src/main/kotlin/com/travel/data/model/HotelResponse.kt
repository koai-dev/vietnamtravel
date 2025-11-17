package com.travel.data.model

import com.travel.data.model.RatingDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class HotelResponse(
    val id: Long,
    val name: String, // This will be the localized name
    val nameVi: String,
    val nameEn: String,
    val slug: String,
    val description: String, // This will be the localized description
    val descriptionVi: String,
    val descriptionEn: String,
    val address: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val addressLink: String?,
    val contact: ContactInfoResponse?,
    val images: List<String> = emptyList(),
    val minPrice: Double?,
    val maxPrice: Double?,
    val rating: Float,
    val reviewCount: Int,
    val viewsCount: Long,
    val favoritesCount: Int,
    val ratingDetails: RatingDetailResponse? = null,
    val amenities: List<String> = emptyList(),
    val checkInTime: String?,
    val checkOutTime: String?,
    val cancellationPolicy: String?,
    val childPolicy: String?,
    val petPolicy: String?,
    val tags: List<String> = emptyList(),
    val externalBookingLinks: List<String> = emptyList(),
    val hostId: Long?,
    val hostName: String? = null,
    val hostAvatar: String? = null,
)