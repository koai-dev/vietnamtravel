package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateBookingRequest(
    val hotelId: Long,
    val roomId: Long,
    val checkIn: String,
    val checkOut: String,
    val totalPrice: Double,
)

@Serializable
data class BookingResponse(
    val id: Long,
    val userId: Long,
    val hotelId: Long,
    val roomId: Long,
    val checkIn: String,
    val checkOut: String,
    val totalPrice: Double,
    val status: String,
)
