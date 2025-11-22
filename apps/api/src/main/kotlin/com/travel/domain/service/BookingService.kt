package com.travel.domain.service

import com.travel.core.distributedLock
import com.travel.domain.model.Booking
import com.travel.domain.repository.BookingRepository
import com.travel.domain.repository.RedisRepository
import com.travel.domain.repository.RoomRepository

class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository,
    private val redisRepository: RedisRepository,
) {
    suspend fun createBooking(booking: Booking): Booking {
        val lockKey = "lock:booking:${booking.roomId}"
        return distributedLock(redisRepository, lockKey, 10) {
            val room =
                roomRepository.findById(booking.roomId)
                    ?: throw Exception("Room not found")

            if (room.availableRooms <= 0) {
                throw Exception("No available rooms")
            }

            roomRepository.updateAvailableRooms(booking.roomId, room.availableRooms - 1)
            bookingRepository.save(booking)
        }
    }

    suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<com.travel.data.model.BookingDashboardResponse>, Long> {
        return bookingRepository.getAll(page, pageSize)
    }
}
