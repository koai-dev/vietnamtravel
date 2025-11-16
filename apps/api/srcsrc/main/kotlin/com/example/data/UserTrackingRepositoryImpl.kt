package com.example.data

import com.example.application.TrackingDataPoint
import com.example.domain.UserTrackingRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class UserTrackingRepositoryImpl : UserTrackingRepository {
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
