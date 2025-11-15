package com.example.domain

import com.example.application.TrackingStatsResponse
import com.example.data.UserTracking
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TrackingServiceImpl : TrackingService {
    override suspend fun getStats(): TrackingStatsResponse {
        return transaction {
            val trackingData = UserTracking.selectAll().map {
                it[UserTracking.platform] to it[UserTracking.endpoint]
            }

            val totalRequests = trackingData.size.toLong()
            val requestsByPlatform = trackingData.groupingBy { it.first }.eachCount().mapValues { it.value.toLong() }
            val requestsByEndpoint = trackingData.groupingBy { it.second }.eachCount().mapValues { it.value.toLong() }

            TrackingStatsResponse(
                totalRequests = totalRequests,
                requestsByPlatform = requestsByPlatform,
                requestsByEndpoint = requestsByEndpoint
            )
        }
    }
}