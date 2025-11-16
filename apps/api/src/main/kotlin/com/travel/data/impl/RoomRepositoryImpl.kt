package com.travel.data.impl

import com.travel.data.table.Rooms
import com.travel.domain.model.Room
import com.travel.domain.repository.RoomRepository
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class RoomRepositoryImpl : RoomRepository {
    override suspend fun findById(id: Long): Room? = newSuspendedTransaction {
        Rooms.selectAll().where { Rooms.id eq id }.map { it.toRoom() }.singleOrNull()
    }

    override suspend fun updateAvailableRooms(id: Long, availableRooms: Int) {
        newSuspendedTransaction {
            Rooms.update({ Rooms.id eq id }) {
                it[Rooms.availableRooms] = availableRooms
            }
        }
    }
}

private fun ResultRow.toRoom(): Room = Room(
    id = this[Rooms.id],
    hotelId = this[Rooms.hotelId],
    roomTypeVi = this[Rooms.roomTypeVi] ?: "",
    roomTypeEn = this[Rooms.roomTypeEn] ?: "",
    maxGuest = this[Rooms.maxGuest] ?: 0,
    pricePerNight = this[Rooms.pricePerNight]?.toDouble() ?: 0.0,
    totalRooms = this[Rooms.totalRooms] ?: 0,
    availableRooms = this[Rooms.availableRooms] ?: 0,
    amenities = this[Rooms.amenities]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
)
