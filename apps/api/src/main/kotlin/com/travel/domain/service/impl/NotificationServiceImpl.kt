package com.travel.domain.service.impl

import com.travel.data.model.CreateNotificationRequest
import com.travel.domain.model.Notification
import com.travel.domain.repository.NotificationRepository
import com.travel.domain.repository.UserRepository
import com.travel.domain.service.NotificationService

class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
) : NotificationService {
    override suspend fun create(
        userId: Long,
        request: CreateNotificationRequest,
    ): Notification {
        // Validate userId exists
        userRepository.findById(userId) ?: throw Exception("User with id $userId not found")
        return notificationRepository.create(userId, request)
    }

    override suspend fun getByUser(
        userId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<Notification>, Long> {
        // Validate userId exists
        userRepository.findById(userId) ?: throw Exception("User with id $userId not found")
        return notificationRepository.getByUser(userId, page, pageSize)
    }

    override suspend fun markAsRead(notificationId: Long): Boolean {
        return notificationRepository.markAsRead(notificationId)
    }

    override suspend fun delete(notificationId: Long): Boolean {
        return notificationRepository.delete(notificationId)
    }
}
