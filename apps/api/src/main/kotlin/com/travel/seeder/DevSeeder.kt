package com.travel.seeder

import com.travel.domain.model.NotificationType
import com.travel.domain.repository.NotificationRepository
import com.travel.presentation.model.CreateNotificationRequest
import kotlinx.coroutines.runBlocking

class DevSeeder(private val notificationRepository: NotificationRepository) {

    fun seedNotifications() {
        runBlocking {
            if (notificationRepository.getByUser(1).isEmpty()) {
                val notificationTypes = listOf(
                    NotificationType.SYSTEM,
                    NotificationType.PROMOTION,
                    NotificationType.BOOKING,
                    NotificationType.UPDATE
                )

                notificationTypes.forEach { type ->
                    repeat(5) {
                        val userId = (1..3).random().toLong()
                        notificationRepository.create(
                            userId,
                            CreateNotificationRequest(
                                title = "$type Notification Title ${it + 1}",
                                content = "This is the content for the $type notification ${it + 1}.",
                                type = type
                            )
                        )
                    }
                }
            }
        }
    }
}
