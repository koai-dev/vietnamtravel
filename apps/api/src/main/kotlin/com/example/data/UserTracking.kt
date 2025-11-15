package com.example.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object UserTracking : Table() {
    val id = long("id").autoIncrement()
    val userId = long("user_id").nullable()
    val device = varchar("device", 255).nullable()
    val platform = varchar("platform", 255).nullable()
    val endpoint = varchar("endpoint", 255)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
