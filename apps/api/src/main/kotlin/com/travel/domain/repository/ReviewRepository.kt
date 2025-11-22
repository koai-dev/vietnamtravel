package com.travel.domain.repository

import com.travel.data.model.ReviewResponse

interface ReviewRepository {
    suspend fun getRecentReviews(limit: Int): List<ReviewResponse>

    suspend fun count(): Long

    suspend fun getAll(
        page: Int = 1,
        pageSize: Int = 20,
    ): Pair<List<ReviewResponse>, Long>

    suspend fun getByHotel(
        hotelId: Long,
        page: Int = 1,
        pageSize: Int = 20,
    ): Pair<List<ReviewResponse>, Long>

    suspend fun getById(id: Long): ReviewResponse?

    suspend fun create(
        userId: Long?,
        hotelId: Long,
        rating: Int?,
        comment: String?,
    ): ReviewResponse

    suspend fun update(
        id: Long,
        rating: Int?,
        comment: String?,
    ): ReviewResponse?

    suspend fun delete(id: Long): Boolean
}
