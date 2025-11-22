package com.travel.data.impl

import com.travel.data.mapper.toJsonString
import com.travel.data.mapper.toNotification
import com.travel.data.model.CreateNotificationRequest
import com.travel.data.table.Notifications
import com.travel.domain.model.Notification
import com.travel.domain.repository.NotificationRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
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

    override suspend fun getByUser(
        userId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<Notification>, Long> =
        newSuspendedTransaction {
            val query = Notifications.selectAll().where { Notifications.userId eq userId }
            val total = query.count()
            val items = query
                .orderBy(Notifications.createdAt, SortOrder.DESC)
                .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                .map { it.toNotification() }
            Pair(items, total)
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
