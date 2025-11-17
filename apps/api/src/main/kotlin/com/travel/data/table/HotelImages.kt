package com.travel.data.table

import org.jetbrains.exposed.sql.Table

object HotelImages : Table("hotel_images") {
    val id = long("id").autoIncrement()
    val hotelId = long("hotel_id").references(Hotels.id)
    val url = text("url")
    val isCover = bool("is_cover").default(false)

    override val primaryKey = PrimaryKey(id)
}
