package com.travel.data.impl

import com.travel.data.model.TrackingDataPoint
import com.travel.data.model.UserTrackingDTO
import com.travel.data.table.UserTracking
import com.travel.domain.service.UserTrackingRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

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
    override suspend fun getSummaryByRange(range: String, date: String?): List<TrackingDataPoint> {
        // Mock data for now
        val now = date?.let { Instant.parse(it) } ?: Instant.now()
        val dataPoints = mutableListOf<TrackingDataPoint>()
        val (count, unit) = when (range) {
            "hour" -> Pair(24, ChronoUnit.HOURS)
            "day" -> Pair(30, ChronoUnit.DAYS)
            "week" -> Pair(12, ChronoUnit.WEEKS)
            "month" -> Pair(12, ChronoUnit.MONTHS)
            "year" -> Pair(5, ChronoUnit.YEARS)
            else -> Pair(30, ChronoUnit.DAYS)
        }

        for (i in 0 until count) {
            val timestamp = now.minus(i.toLong(), unit)
            dataPoints.add(
                TrackingDataPoint(
                    timestamp = timestamp.toString(),
                    visits = Random.nextInt(100, 500)
                )
            )
        }
        return dataPoints
    }
}