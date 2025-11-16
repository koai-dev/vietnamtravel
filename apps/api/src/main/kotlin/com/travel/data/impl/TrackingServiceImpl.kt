package com.travel.data.impl

import com.travel.data.model.TrackingStatsResponse
import com.travel.data.model.TrackingSummaryResponse
import com.travel.data.table.UserTracking
import com.travel.domain.service.TrackingService
import com.travel.domain.service.UserTrackingRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.temporal.ChronoUnit

class TrackingServiceImpl(private val userTrackingRepository: UserTrackingRepository) : TrackingService {
    override suspend fun getStats(period: String?, startDate: String?, endDate: String?): TrackingStatsResponse {
        return transaction {
            val query = UserTracking.selectAll()

            val now = Instant.now()
            val start: Instant? = when (period) {
                "day" -> now.minus(1, ChronoUnit.DAYS)
                "week" -> now.minus(7, ChronoUnit.DAYS)
                "month" -> now.minus(30, ChronoUnit.DAYS)
                "year" -> now.minus(365, ChronoUnit.DAYS)
                else -> startDate?.let { Instant.parse(it) }
            }
            val end: Instant? = endDate?.let { Instant.parse(it) }

            if (start != null) {
                query.adjustWhere { UserTracking.createdAt.greaterEq(start) }
            }
            if (end != null) {
                query.adjustWhere { UserTracking.createdAt.lessEq(end) }
            }


            val trackingData = query.map {
                it[UserTracking.platform] to it[UserTracking.endpoint]
            }

            val totalRequests = trackingData.size.toLong()
            val requestsByPlatform =
                trackingData.groupingBy { it.first.orEmpty() }.eachCount().mapValues { it.value.toLong() }
            val requestsByEndpoint = trackingData.groupingBy { it.second }.eachCount().mapValues { it.value.toLong() }

            TrackingStatsResponse(
                totalRequests = totalRequests,
                requestsByPlatform = requestsByPlatform,
                requestsByEndpoint = requestsByEndpoint
            )
        }
    }

    override suspend fun getTrackingSummary(range: String, date: String?): TrackingSummaryResponse {
        val data = userTrackingRepository.getSummaryByRange(range, date)
        return TrackingSummaryResponse(range, data)
    }
}