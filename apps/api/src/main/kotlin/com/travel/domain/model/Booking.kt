package com.travel.domain.model

import java.time.LocalDate

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