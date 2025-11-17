package com.travel.domain.model

import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val image: String? = null,
    val type: NotificationType,
    val isRead: Boolean,
    val createdAt: LocalDateTime,
    val metadata: Map<String, String>? = null,
)
