package com.travel.domain.repository

import com.travel.data.model.CreateNotificationRequest
import com.travel.domain.model.Notification

interface NotificationRepository {
    suspend fun create(
        userId: Long,
        request: CreateNotificationRequest,
    ): Notification

    suspend fun getByUser(
        userId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<Notification>, Long>

    suspend fun markAsRead(notificationId: Long): Boolean

    suspend fun delete(notificationId: Long): Boolean
}
