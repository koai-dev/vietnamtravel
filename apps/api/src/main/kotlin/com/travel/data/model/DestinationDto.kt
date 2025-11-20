package com.travel.data.model

import aws.smithy.kotlin.runtime.content.BigDecimal
import com.travel.core.BigDecimalSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DestinationResponse(
    val id: Long,
    val name: String,
    val nameVi: String = "",
    val nameEn: String = "",
    val description: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val type: String? = null,
    val images: List<String> = emptyList(),
    val parentId: Long? = null,
    val city: String? = null,
    val avgRating: Double = 5.0,
    val reviewCount: Int = 0,
    val viewsCount: Long = 0,
    val status: String? = null,
    val children: List<DestinationResponse> = emptyList(),
    val foods: List<LocalFoodResponse> = emptyList(),
    val restaurants: List<RestaurantResponse> = emptyList(),
)

@Serializable
data class DestinationRequest(
    val nameVi: String = "",
    val nameEn: String = "",
    val descriptionVi: String = "",
    val descriptionEn: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val type: String = "",
    val images: List<String> = emptyList(),
    val parentId: Long? = null,
    val tempUrlMap: Map<String, String>? = null,
    val slug: String = nameEn,
    val address: String? = null,
    val city: String? = null,
    val tags: List<String> = emptyList(),
    val bestTimeToVisit: String? = null,
    val openingHours: String? = null,
    val priceFrom: Double? = null,
    val priceTo: Double? = null,
    val externalLinks: List<String> = emptyList(),
    val addressLink: String? = null,
    val status: String = "active",
    val sortOrder: Int = 0,
)

@Serializable
data class RatingRequest(
    val rating: Double = 5.0,
)

@Serializable
data class DestinationResponseDetail(
    val id: Long,
    val name: String = "",
    val description: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val type: String? = null,
    val images: List<String> = emptyList(),
    val parentId: Long? = null,
    val slug: String? = null,
    val address: String? = null,
    val city: String? = null,
    val tags: List<String> = emptyList(),
    val bestTimeToVisit: String?,
    val openingHours: String?,
    @Serializable(with = BigDecimalSerializer::class)
    val priceFrom: BigDecimal?,
    @Serializable(with = BigDecimalSerializer::class)
    val priceTo: BigDecimal? = null,
    val externalLinks: List<String> = emptyList(),
    val addressLink: String?,
    val avgRating: Double = 5.0,
    val reviewCount: Int = 0,
    val viewsCount: Long = 0,
    val favoritesCount: Int = 0,
    val status: String?,
    val sortOrder: Int = 0,
    val children: List<DestinationResponse> = emptyList(),
    val foods: List<LocalFoodResponse> = emptyList(),
    val restaurants: List<RestaurantResponse> = emptyList(),
)
