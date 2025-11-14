package com.example.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Reviews : Table("reviews") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id).nullable()
    val hotelId = long("hotel_id").references(Hotels.id)
    val rating = integer("rating").nullable()
    val comment = text("comment").nullable()
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
