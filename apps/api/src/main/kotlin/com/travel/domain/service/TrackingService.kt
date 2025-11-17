package com.travel.domain.service

import com.travel.data.model.TrackingStatsResponse
import com.travel.data.model.TrackingSummaryResponse

interface TrackingService {
    suspend fun getStats(
        period: String?,
        startDate: String?,
        endDate: String?,
    ): TrackingStatsResponse

    suspend fun getTrackingSummary(
        range: String,
        date: String?,
    ): TrackingSummaryResponse
}
