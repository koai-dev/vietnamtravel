package com.example.data

import org.jetbrains.exposed.sql.Table

object Favorites : Table("favorites") {
    val userId = long("user_id").references(Users.id)
    val hotelId = long("hotel_id").references(Hotels.id)

    override val primaryKey = PrimaryKey(userId, hotelId)
}
