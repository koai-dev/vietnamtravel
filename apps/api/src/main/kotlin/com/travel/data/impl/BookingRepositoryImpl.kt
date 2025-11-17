package com.travel.data.impl

import com.travel.data.table.BookingStatus
import com.travel.data.table.Bookings
import com.travel.domain.model.Booking
import com.travel.domain.repository.BookingRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.math.BigDecimal

class BookingRepositoryImpl : BookingRepository {
    override suspend fun save(booking: Booking): Booking =
        newSuspendedTransaction {
            val id =
                Bookings.insert {
                    it[userId] = booking.userId
                    it[hotelId] = booking.hotelId
                    it[roomId] = booking.roomId
                    it[checkIn] = booking.checkIn
                    it[checkOut] = booking.checkOut
                    it[totalPrice] = BigDecimal.valueOf(booking.totalPrice)
                    it[status] = BookingStatus.valueOf(booking.status)
                } get Bookings.id
            booking.copy(id = id)
        }
}
