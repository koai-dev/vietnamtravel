package com.travel.domain.repository

import com.travel.domain.model.Tour

interface TourRepository {
    suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<Tour>, Long>

    suspend fun findById(id: Long): Tour?

    suspend fun getPopular(): List<Tour>
}
