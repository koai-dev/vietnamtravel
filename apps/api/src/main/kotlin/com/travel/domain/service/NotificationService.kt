package com.travel.domain.service

import com.travel.data.model.CreateNotificationRequest
import com.travel.domain.model.Notification

interface NotificationService {
    suspend fun create(
        userId: Long,
        request: CreateNotificationRequest,
    ): Notification

    suspend fun getByUser(userId: Long): List<Notification>

    suspend fun markAsRead(notificationId: Long): Boolean

    suspend fun delete(notificationId: Long): Boolean
}
