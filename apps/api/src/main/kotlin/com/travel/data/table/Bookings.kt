package com.travel.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Bookings : Table("bookings") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id)
    val hotelId = long("hotel_id").references(Hotels.id)
    val roomId = long("room_id").references(Rooms.id)
    val checkIn = date("check_in")
    val checkOut = date("check_out")
    val totalPrice = decimal("total_price", 10, 2).nullable()
    val status = enumerationByName("status", 10, BookingStatus::class)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED,
}
