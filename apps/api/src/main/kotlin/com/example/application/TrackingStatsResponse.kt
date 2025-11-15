package com.example.application

import kotlinx.serialization.Serializable

@Serializable
data class TrackingStatsResponse(
    val totalRequests: Long,
    val requestsByPlatform: Map<String, Long>,
    val requestsByEndpoint: Map<String, Long>
)
