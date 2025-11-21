package com.travel.domain.repository

import com.travel.data.model.DestinationRequest
import com.travel.domain.model.Destination
import com.travel.domain.model.DestinationDetail

interface DestinationRepository {
    suspend fun getAll(): List<Destination>

    suspend fun findById(id: Long): Destination?

    suspend fun deleteDestination(id: Long): Boolean

    suspend fun count(): Long

    suspend fun findByIdDetail(id: Long): DestinationDetail?

    suspend fun findChildren(id: Long): List<Destination>

    suspend fun findTree(id: Long): Destination?

    suspend fun listRoot(): List<Destination>

    suspend fun create(request: DestinationRequest): Long

    suspend fun update(
        id: Long,
        request: DestinationRequest,
    ): Int

    suspend fun search(
        query: String,
        types: List<com.travel.data.table.DestinationType>,
    ): List<Destination>
}
