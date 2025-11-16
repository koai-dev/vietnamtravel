package com.travel.data.mapper

import com.travel.data.model.BookingResponse
import com.travel.domain.model.Booking

fun Booking.toBookingResponse() = BookingResponse(
    id = id,
    userId = userId,
    hotelId = hotelId,
    roomId = roomId,
    checkIn = checkIn.toString(),
    checkOut = checkOut.toString(),
    totalPrice = totalPrice,
    status = status
)