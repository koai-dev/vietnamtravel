package com.travel.data.table

import org.jetbrains.exposed.sql.Table

object Rooms : Table("rooms") {
    val id = long("id").autoIncrement()
    val hotelId = long("hotel_id").references(Hotels.id)
    val roomTypeVi = varchar("room_type_vi", 255).nullable()
    val roomTypeEn = varchar("room_type_en", 255).nullable()
    val maxGuest = integer("max_guest").nullable()
    val pricePerNight = decimal("price_per_night", 10, 2).nullable()
    val totalRooms = integer("total_rooms").nullable()
    val availableRooms = integer("available_rooms").nullable()
    val amenities = text("amenities").nullable() // Storing JSON as TEXT

    override val primaryKey = PrimaryKey(id)
}