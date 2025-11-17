package com.travel.data.impl

import com.travel.data.mapper.toJsonString
import com.travel.data.mapper.toNotification
import com.travel.data.table.Notifications
import com.travel.domain.model.Notification
import com.travel.domain.repository.NotificationRepository
import com.travel.presentation.model.CreateNotificationRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class NotificationRepositoryImpl : NotificationRepository {
    override suspend fun create(
        userId: Long,
        request: CreateNotificationRequest,
    ): Notification {
        val notificationId =
            newSuspendedTransaction {
                Notifications.insert {
                    it[this.userId] = userId
                    it[this.title] = request.title
                    it[this.content] = request.content
                    it[this.image] = request.image
                    it[this.type] = request.type
                    it[this.metadata] = request.metadata?.toJsonString()
                    it[this.createdAt] = LocalDateTime.now()
                } get Notifications.id
            }
        return getById(notificationId)!!
    }

    override suspend fun getByUser(userId: Long): List<Notification> =
        newSuspendedTransaction {
            Notifications.selectAll().where { Notifications.userId eq userId }
                .orderBy(Notifications.createdAt, SortOrder.DESC)
                .map { it.toNotification() }
        }

    override suspend fun markAsRead(notificationId: Long): Boolean {
        return newSuspendedTransaction {
            Notifications.update({ Notifications.id eq notificationId }) {
                it[isRead] = true
            } > 0
        }
    }

    override suspend fun delete(notificationId: Long): Boolean {
        return newSuspendedTransaction {
            Notifications.deleteWhere { Notifications.id eq notificationId } > 0
        }
    }

    private suspend fun getById(id: Long): Notification? =
        newSuspendedTransaction {
            Notifications.selectAll().where { Notifications.id eq id }
                .map { it.toNotification() }
                .singleOrNull()
        }
}
