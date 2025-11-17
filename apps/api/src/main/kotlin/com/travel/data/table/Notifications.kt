package com.travel.data.table

import com.travel.domain.model.NotificationType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Notifications : Table("notifications") {
    val id = long("id").autoIncrement()
    val userId = long("user_id").references(Users.id)
    val title = varchar("title", 255)
    val content = text("content")
    val image = text("image").nullable()
    val type = enumerationByName("type", 20, NotificationType::class)
    val isRead = bool("is_read").default(false)
    val metadata = text("metadata").nullable()
    val createdAt = datetime("created_at")

    override val primaryKey = PrimaryKey(id)
}
