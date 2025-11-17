package com.travel.presentation.controller

import com.travel.data.mapper.toBookingResponse
import com.travel.data.model.BookingResponse
import com.travel.data.model.CreateBookingRequest
import com.travel.domain.model.Booking
import com.travel.domain.service.BookingService
import java.time.LocalDate

class BookingController(private val bookingService: BookingService) {
    suspend fun createBooking(
        userId: Long,
        request: CreateBookingRequest,
    ): BookingResponse {
        val booking =
            Booking(
                userId = userId,
                hotelId = request.hotelId,
                roomId = request.roomId,
                checkIn = LocalDate.parse(request.checkIn),
                checkOut = LocalDate.parse(request.checkOut),
                totalPrice = request.totalPrice,
                status = "pending",
            )
        return bookingService.createBooking(booking).toBookingResponse()
    }
}
