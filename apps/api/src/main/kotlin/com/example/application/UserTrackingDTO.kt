package com.example.application

import kotlinx.serialization.Serializable

@Serializable
data class UserTrackingDTO(
    val userId: Long?,
    val device: String?,
    val platform: String?,
    val endpoint: String
)
