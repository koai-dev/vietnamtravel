package com.travel.domain.repository

import com.travel.domain.model.Destination

interface DestinationRepository {
    suspend fun getAll(): List<Destination>
    suspend fun findById(id: Long): Destination?
}