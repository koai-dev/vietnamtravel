package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackingSummaryResponse(
    val range: String,
    val data: List<TrackingDataPoint>,
)

@Serializable
data class TrackingDataPoint(
    val timestamp: String,
    val visits: Int,
)
