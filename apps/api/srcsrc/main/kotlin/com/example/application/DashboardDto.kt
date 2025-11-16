package com.example.application

import kotlinx.serialization.Serializable

@Serializable
data class NewUsersResponse(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val items: List<UserResponse>
)
