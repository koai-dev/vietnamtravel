package com.travel.data.model

import com.travel.domain.model.NotificationType
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val image: String? = null,
    val type: NotificationType,
    val isRead: Boolean,
    val createdAt: String,
    val metadata: Map<String, String>? = null,
)
