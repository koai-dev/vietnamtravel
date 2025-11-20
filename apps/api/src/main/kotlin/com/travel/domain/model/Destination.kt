package com.travel.domain.model

import aws.smithy.kotlin.runtime.content.BigDecimal
import com.travel.core.BigDecimalSerializer
import com.travel.data.table.DestinationType
import kotlinx.serialization.Serializable

@Serializable
data class Destination(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: DestinationType?,
    val images: List<String>,
    val parentId: Long?,
    val children: List<Destination> = emptyList(),
    val foods: List<LocalFood> = emptyList(),
    val restaurants: List<Restaurant> = emptyList(),
    val city: String?,
    val avgRating: Double,
    val reviewCount: Int,
    val viewsCount: Long,
    val status: String?,
)

@Serializable
data class DestinationDetail(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: DestinationType?,
    val images: List<String>,
    val parentId: Long?,
    val children: List<Destination> = emptyList(),
    val foods: List<LocalFood> = emptyList(),
    val restaurants: List<Restaurant> = emptyList(),
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
)
