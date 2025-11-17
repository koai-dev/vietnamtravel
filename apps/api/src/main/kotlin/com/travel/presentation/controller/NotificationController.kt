package com.travel.presentation.controller

import com.travel.domain.service.NotificationService
import com.travel.data.model.CreateNotificationRequest
import com.travel.data.model.NotificationResponse
import java.time.format.DateTimeFormatter

class NotificationController(private val notificationService: NotificationService) {
    suspend fun createNotification(
        userId: Long,
        request: CreateNotificationRequest,
    ): NotificationResponse {
        val notification = notificationService.create(userId, request)
        return notification.toNotificationResponse()
    }

    suspend fun getNotificationsByUser(userId: Long): List<NotificationResponse> {
        return notificationService.getByUser(userId).map { it.toNotificationResponse() }
    }

    suspend fun markAsRead(notificationId: Long): Boolean {
        return notificationService.markAsRead(notificationId)
    }

    suspend fun deleteNotification(notificationId: Long): Boolean {
        return notificationService.delete(notificationId)
    }

    private fun com.travel.domain.model.Notification.toNotificationResponse(): NotificationResponse {
        return NotificationResponse(
            id = this.id,
            userId = this.userId,
            title = this.title,
            content = this.content,
            image = this.image,
            type = this.type,
            isRead = this.isRead,
            createdAt = this.createdAt.format(DateTimeFormatter.ISO_DATE_TIME),
            metadata = this.metadata,
        )
    }
}
