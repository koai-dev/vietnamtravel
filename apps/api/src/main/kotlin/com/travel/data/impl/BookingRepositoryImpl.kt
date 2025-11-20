package com.travel.data.impl

import com.travel.data.model.BookingDashboardResponse
import com.travel.data.table.BookingStatus
import com.travel.data.table.Bookings
import com.travel.data.table.Hotels
import com.travel.data.table.Users
import com.travel.domain.model.Booking
import com.travel.domain.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.math.BigDecimal

class BookingRepositoryImpl : BookingRepository {
    override suspend fun save(booking: Booking): Booking = newSuspendedTransaction(Dispatchers.IO) {
        val id = Bookings.insert {
            it[userId] = booking.userId
            it[hotelId] = booking.hotelId
            it[roomId] = booking.roomId
            it[checkIn] = booking.checkIn
            it[checkOut] = booking.checkOut
            it[totalPrice] = BigDecimal.valueOf(booking.totalPrice)
            it[status] = BookingStatus.valueOf(booking.status)
            it[createdAt] = java.time.LocalDateTime.now()
            it[updatedAt] = java.time.LocalDateTime.now()
        } get Bookings.id

        booking.copy(id = id)
    }

    override suspend fun count(): Long = newSuspendedTransaction(Dispatchers.IO) {
        Bookings.selectAll().count()
    }

    override suspend fun countByStatus(status: String): Long = newSuspendedTransaction(Dispatchers.IO) {
        Bookings.select { Bookings.status eq BookingStatus.valueOf(status) }.count()
    }

    override suspend fun sumTotalPrice(): Double = newSuspendedTransaction(Dispatchers.IO) {
        Bookings.slice(Bookings.totalPrice.sum()).selectAll().firstOrNull()?.get(Bookings.totalPrice.sum())?.toDouble() ?: 0.0
    }

    override suspend fun getRecentBookings(limit: Int): List<BookingDashboardResponse> = newSuspendedTransaction(Dispatchers.IO) {
        Bookings
            .join(Users, JoinType.LEFT, Bookings.userId, Users.id)
            .join(Hotels, JoinType.INNER, Bookings.hotelId, Hotels.id)
            .selectAll()
            .orderBy(Bookings.createdAt, SortOrder.DESC)
            .limit(limit)
            .map {
                BookingDashboardResponse(
                    id = it[Bookings.id],
                    userId = it[Bookings.userId],
                    hotelId = it[Bookings.hotelId],
                    roomId = it[Bookings.roomId],
                    checkIn = it[Bookings.checkIn].toString(),
                    checkOut = it[Bookings.checkOut].toString(),
                    totalPrice = it[Bookings.totalPrice]?.toDouble() ?: 0.0,
                    status = it[Bookings.status].name,
                    hotelName = it[Hotels.nameVi],
                    userName = it[Users.name]
                )
            }
    }
}
