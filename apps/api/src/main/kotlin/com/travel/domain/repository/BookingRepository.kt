package com.travel.domain.repository

import com.travel.domain.model.Booking

interface BookingRepository {
    suspend fun save(booking: Booking): Booking
}