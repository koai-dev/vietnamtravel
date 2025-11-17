package com.travel.data.model

import com.travel.domain.model.NotificationType
import kotlinx.serialization.Serializable

@Serializable
data class CreateNotificationRequest(
    val title: String,
    val content: String,
    val image: String? = null,
    val type: NotificationType,
    val metadata: Map<String, String>? = null,
)