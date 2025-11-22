package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateReviewRequest(
    val userId: Long?,
    val hotelId: Long,
    val rating: Int?,
    val comment: String?,
)