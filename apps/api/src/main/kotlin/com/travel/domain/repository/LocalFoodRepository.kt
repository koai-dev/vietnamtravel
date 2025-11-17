package com.travel.domain.repository

import com.travel.domain.model.LocalFood

interface LocalFoodRepository {
    suspend fun create(localFood: LocalFood): LocalFood

    suspend fun update(
        id: Long,
        localFood: LocalFood,
    ): LocalFood?

    suspend fun delete(id: Long)

    suspend fun getById(id: Long): LocalFood?

    suspend fun listByDestinationId(destinationId: Long): List<LocalFood>
}
