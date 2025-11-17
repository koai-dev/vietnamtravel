package com.travel.domain.repository

import com.travel.domain.model.Notification
import com.travel.presentation.model.CreateNotificationRequest

interface NotificationRepository {
    suspend fun create(userId: Long, request: CreateNotificationRequest): Notification
    suspend fun getByUser(userId: Long): List<Notification>
    suspend fun markAsRead(notificationId: Long): Boolean
    suspend fun delete(notificationId: Long): Boolean
}
