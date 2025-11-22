package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateReviewRequest(
    val rating: Int?,
    val comment: String?,
)