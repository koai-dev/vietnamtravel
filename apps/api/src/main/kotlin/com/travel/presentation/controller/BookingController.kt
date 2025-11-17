package com.travel.presentation.controller

import com.travel.data.mapper.toBookingResponse
import com.travel.data.model.CreateBookingRequest
import com.travel.domain.model.Booking
import com.travel.domain.service.BookingService
import io.ktor.server.application.ApplicationCall
import java.time.LocalDate

class BookingController(private val bookingService: BookingService) : BaseController() {
    suspend fun createBooking(
        call: ApplicationCall,
        userId: Long,
        request: CreateBookingRequest,
    ) {
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
        val createdBooking = bookingService.createBooking(booking).toBookingResponse()
        respondWith(call, createdBooking)
    }
}
