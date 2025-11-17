package com.travel.data.mapper

import com.travel.data.table.Notifications
import com.travel.domain.model.Notification
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toNotification(): Notification {
    val metadataJson = this[Notifications.metadata]
    val metadata =
        metadataJson?.let {
            Json.decodeFromString<Map<String, String>>(it)
        }
    return Notification(
        id = this[Notifications.id],
        userId = this[Notifications.userId],
        title = this[Notifications.title],
        content = this[Notifications.content],
        image = this[Notifications.image],
        type = this[Notifications.type],
        isRead = this[Notifications.isRead],
        createdAt = this[Notifications.createdAt],
        metadata = metadata,
    )
}

fun Map<String, String>.toJsonString(): String {
    return Json.encodeToString(this)
}
