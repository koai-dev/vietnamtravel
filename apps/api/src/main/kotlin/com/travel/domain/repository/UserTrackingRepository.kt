package com.travel.domain.repository

import com.travel.data.model.TrackingDataPoint

interface UserTrackingRepository {
    suspend fun getSummaryByRange(range: String, date: String?): List<TrackingDataPoint>
}