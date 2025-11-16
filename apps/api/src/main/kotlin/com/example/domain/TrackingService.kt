package com.example.domain

import com.example.application.TrackingStatsResponse
import com.example.application.TrackingSummaryResponse

interface TrackingService {
    suspend fun getStats(period: String?, startDate: String?, endDate: String?): TrackingStatsResponse
    suspend fun getTrackingSummary(range: String, date: String?): TrackingSummaryResponse
}