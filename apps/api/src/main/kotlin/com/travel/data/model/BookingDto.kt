package com.travel.data.model

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Serializable
data class CreateBookingRequest(
    val hotelId: Long,
    val roomId: Long,
    val checkIn: String,
    val checkOut: String,
    val totalPrice: Double,
)

fun CreateBookingRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    try {
        LocalDate.parse(checkIn)
    } catch (e: DateTimeParseException) {
        errors.add("Invalid check-in date format. Use YYYY-MM-DD.")
    }
    try {
        LocalDate.parse(checkOut)
    } catch (e: DateTimeParseException) {
        errors.add("Invalid check-out date format. Use YYYY-MM-DD.")
    }
    if (totalPrice <= 0) {
        errors.add("Total price must be positive.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

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
