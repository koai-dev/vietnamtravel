package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val id: Long,
    val userId: Long?,
    val hotelId: Long,
    val rating: Int?,
    val comment: String?,
    val createdAt: String,
    val userName: String?,
    val hotelName: String?
)
