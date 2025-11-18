package com.travel.data.model

import aws.smithy.kotlin.runtime.content.BigDecimal
import com.travel.core.BigDecimalSerializer
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
    val children: List<DestinationResponse> = emptyList(),
    val foods: List<LocalFoodResponse> = emptyList(),
    val restaurants: List<RestaurantResponse> = emptyList(),
)

@Serializable
data class DestinationRequest(
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: String,
    val images: List<String>,
    val parentId: Long?,
    val tempUrlMap: Map<String, String>? = null,
    val slug: String,
    val address: String?,
    val city: String?,
    val tags: List<String>,
    val bestTimeToVisit: String?,
    val openingHours: String?,
    val priceFrom: Double?,
    val priceTo: Double?,
    val externalLinks: List<String>,
    val addressLink: String?,
    val status: String,
    val sortOrder: Int,
)

@Serializable
data class RatingRequest(
    val rating: Double,
)

@Serializable
data class DestinationResponseDetail(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: String?,
    val images: List<String>,
    val parentId: Long?,
    val slug: String?,
    val address: String?,
    val city: String?,
    val tags: List<String>,
    val bestTimeToVisit: String?,
    val openingHours: String?,
    @Serializable(with = BigDecimalSerializer::class)
    val priceFrom: BigDecimal?,
    @Serializable(with = BigDecimalSerializer::class)
    val priceTo: BigDecimal?,
    val externalLinks: List<String>,
    val addressLink: String?,
    val avgRating: Double,
    val reviewCount: Int,
    val viewsCount: Long,
    val favoritesCount: Int,
    val status: String?,
    val sortOrder: Int,
    val children: List<DestinationResponse> = emptyList(),
    val foods: List<LocalFoodResponse> = emptyList(),
    val restaurants: List<RestaurantResponse> = emptyList(),
)
