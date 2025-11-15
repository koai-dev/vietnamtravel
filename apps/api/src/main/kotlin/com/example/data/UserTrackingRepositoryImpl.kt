package com.example.data

import com.example.application.UserTrackingDTO
import com.example.domain.UserTrackingRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import java.time.Instant

class UserTrackingRepositoryImpl : UserTrackingRepository {
    override suspend fun add(tracking: UserTrackingDTO) {
        transaction {
            UserTracking.insert {
                it[userId] = tracking.userId
                it[device] = tracking.device
                it[platform] = tracking.platform
                it[endpoint] = tracking.endpoint
                it[os] = tracking.os
                it[osVersion] = tracking.osVersion
                it[ipAddress] = tracking.ipAddress
                it[createdAt] = Instant.now()
            }
        }
    }
}