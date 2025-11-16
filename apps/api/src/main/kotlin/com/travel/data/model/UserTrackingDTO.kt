package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTrackingDTO(
    val userId: Long?,
    val device: String?,
    val platform: String?,
    val endpoint: String,
    val os: String?,
    val osVersion: String?,
    val ipAddress: String?
)