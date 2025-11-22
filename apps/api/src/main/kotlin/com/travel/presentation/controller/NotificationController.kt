package com.travel.presentation.controller

import com.travel.data.model.CreateNotificationRequest
import com.travel.data.model.NotificationResponse
import com.travel.domain.service.NotificationService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import java.time.format.DateTimeFormatter

class NotificationController(private val notificationService: NotificationService) : BaseController() {
    suspend fun createNotification(
        call: ApplicationCall,
        userId: Long,
        request: CreateNotificationRequest,
    ) {
        val notification = notificationService.create(userId, request)
        respondWith(call, notification.toNotificationResponse(), HttpStatusCode.Created.description)
    }

    suspend fun getNotificationsByUser(
        call: ApplicationCall,
        userId: Long,
    ) {
        val (page, pageSize) = getPaginationParams(call)
        val (notifications, total) = notificationService.getByUser(userId, page, pageSize)
        val response = notifications.map { it.toNotificationResponse() }
        
        val totalPages = (total + pageSize - 1) / pageSize
        
        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = response,
                pagination = com.travel.presentation.model.Pagination(
                    page = page,
                    pageSize = pageSize,
                    total = total,
                    totalPages = totalPages.toInt()
                )
            )
        )
    }

    suspend fun markAsRead(
        call: ApplicationCall,
        notificationId: Long,
    ) {
        val result = notificationService.markAsRead(notificationId)
        if (result) {
            respondWith(call, true)
        } else {
            respondWithError(call, "Notification not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun deleteNotification(
        call: ApplicationCall,
        notificationId: Long,
    ) {
        val result = notificationService.delete(notificationId)
        if (result) {
            respondWith(call, true, "Notification deleted successfully")
        } else {
            respondWithError(call, "Notification not found", HttpStatusCode.NotFound)
        }
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
