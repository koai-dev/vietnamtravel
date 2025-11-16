package com.travel.domain.service

import com.travel.data.model.TrackingDataPoint
import com.travel.data.model.UserTrackingDTO

interface UserTrackingRepository {
    suspend fun add(tracking: UserTrackingDTO)
    suspend fun getSummaryByRange(range: String, date: String?): List<TrackingDataPoint>
}