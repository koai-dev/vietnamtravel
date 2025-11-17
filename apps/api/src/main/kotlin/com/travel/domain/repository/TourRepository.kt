package com.travel.domain.repository

import com.travel.domain.model.Tour

interface TourRepository {
    suspend fun getAll(): List<Tour>

    suspend fun findById(id: Long): Tour?

    suspend fun getPopular(): List<Tour>
}
