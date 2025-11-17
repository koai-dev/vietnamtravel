package com.travel.presentation.controller

import com.travel.data.model.TrackingStatsResponse
import com.travel.data.model.TrackingSummaryResponse
import com.travel.domain.service.TrackingService

class TrackingController(private val trackingService: TrackingService) {
    suspend fun getStats(
        period: String?,
        startDate: String?,
        endDate: String?,
    ): TrackingStatsResponse {
        return trackingService.getStats(period, startDate, endDate)
    }

    suspend fun getTrackingSummary(
        range: String,
        date: String?,
    ): TrackingSummaryResponse {
        return trackingService.getTrackingSummary(range, date)
    }
}
