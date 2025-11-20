package com.travel.domain.repository

import com.travel.data.model.BookingDashboardResponse
import com.travel.domain.model.Booking

interface BookingRepository {
    suspend fun save(booking: Booking): Booking
    suspend fun count(): Long
    suspend fun countByStatus(status: String): Long
    suspend fun sumTotalPrice(): Double
    suspend fun getRecentBookings(limit: Int): List<BookingDashboardResponse>
}
