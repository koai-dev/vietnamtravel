package com.example.domain

import com.example.application.TrackingStatsResponse

interface TrackingService {
    suspend fun getStats(): TrackingStatsResponse
}