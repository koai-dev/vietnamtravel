package com.example.domain

import com.example.core.distributedLock
import java.time.LocalDate

class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun createBooking(booking: Booking): Booking {
        val lockKey = "lock:booking:${booking.roomId}"
        return distributedLock(redisRepository, lockKey, 10) {
            val room = roomRepository.findById(booking.roomId)
                ?: throw Exception("Room not found")

            if (room.availableRooms <= 0) {
                throw Exception("No available rooms")
            }

            roomRepository.updateAvailableRooms(booking.roomId, room.availableRooms - 1)
            bookingRepository.save(booking)
        }
    }
}

interface BookingRepository {
    suspend fun save(booking: Booking): Booking
}

interface RoomRepository {
    suspend fun findById(id: Long): Room?
    suspend fun updateAvailableRooms(id: Long, availableRooms: Int)
}

data class Booking(
    val id: Long = 0,
    val userId: Long,
    val hotelId: Long,
    val roomId: Long,
    val checkIn: LocalDate,
    val checkOut: LocalDate,
    val totalPrice: Double,
    val status: String
)

data class Room(
    val id: Long = 0,
    val hotelId: Long,
    val roomTypeVi: String,
    val roomTypeEn: String,
    val maxGuest: Int,
    val pricePerNight: Double,
    val totalRooms: Int,
    val availableRooms: Int,
    val amenities: List<String>
)
