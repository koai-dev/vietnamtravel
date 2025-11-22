package com.travel.domain.repository

import com.travel.domain.model.Room

interface RoomRepository {
    suspend fun findById(id: Long): Room?

    suspend fun updateAvailableRooms(
        id: Long,
        availableRooms: Int,
    )

    suspend fun getAll(
        page: Int = 1,
        pageSize: Int = 20,
    ): Pair<List<Room>, Long>

    suspend fun getByHotel(
        hotelId: Long,
        page: Int = 1,
        pageSize: Int = 20,
    ): Pair<List<Room>, Long>

    suspend fun create(room: Room): Room

    suspend fun update(
        id: Long,
        room: Room,
    ): Room?

    suspend fun delete(id: Long): Boolean
}
