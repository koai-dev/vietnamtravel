package com.travel.domain.repository

import com.travel.data.model.ReviewResponse

interface ReviewRepository {
    suspend fun getRecentReviews(limit: Int): List<ReviewResponse>

    suspend fun count(): Long
}
