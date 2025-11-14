package com.example.data

import com.example.domain.Booking
import com.example.domain.BookingRepository
import com.example.domain.Room
import com.example.domain.RoomRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.math.BigDecimal

class BookingRepositoryImpl : BookingRepository {
    override suspend fun save(booking: Booking): Booking = newSuspendedTransaction {
        val id = Bookings.insert {
            it[userId] = booking.userId
            it[hotelId] = booking.hotelId
            it[roomId] = booking.roomId
            it[checkIn] = booking.checkIn
            it[checkOut] = booking.checkOut
            it[totalPrice] = BigDecimal.valueOf(booking.totalPrice)
            it[status] = BookingStatus.valueOf(booking.status)
        } get Bookings.id
        booking.copy(id = id)
    }
}

class RoomRepositoryImpl : RoomRepository {
    override suspend fun findById(id: Long): Room? = newSuspendedTransaction {
        Rooms.select { Rooms.id eq id }.map { it.toRoom() }.singleOrNull()
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
    amenities = this[Rooms.amenities]?.let { kotlinx.serialization.json.Json.decodeFromString<List<String>>(it) } ?: emptyList()
)
