package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RatingDetailResponse(
    val totalReviews: Int,
    val averageRating: Float,
    val ratingDistribution: Map<Int, Int>, // e.g., {5: 120, 4: 80, 3: 30, 2: 10, 1: 5}
)