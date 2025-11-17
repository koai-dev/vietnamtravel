package com.travel.domain.repository

import com.travel.domain.model.Destination
import com.travel.domain.model.DestinationDetail

interface DestinationRepository {
    suspend fun getAll(): List<Destination>

    suspend fun findById(id: Long): Destination?

    suspend fun findByIdDetail(id: Long): DestinationDetail?

    suspend fun findChildren(id: Long): List<Destination>

    suspend fun findTree(id: Long): Destination?

    suspend fun listRoot(): List<Destination>
}
