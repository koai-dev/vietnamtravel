package com.travel.domain.repository

import com.travel.domain.model.Room

interface RoomRepository {
    suspend fun findById(id: Long): Room?

    suspend fun updateAvailableRooms(
        id: Long,
        availableRooms: Int,
    )
}
