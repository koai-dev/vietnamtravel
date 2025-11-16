package com.travel.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object UserTracking : Table() {
    val id = long("id").autoIncrement()
    val userId = long("user_id").nullable()
    val device = varchar("device", 255).nullable()
    val platform = varchar("platform", 255).nullable()
    val endpoint = varchar("endpoint", 255)
    val os = varchar("os", 255).nullable()
    val osVersion = varchar("os_version", 255).nullable()
    val ipAddress = varchar("ip_address", 255).nullable()
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}