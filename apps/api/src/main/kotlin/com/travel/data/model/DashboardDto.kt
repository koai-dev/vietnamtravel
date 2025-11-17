package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewUsersResponse(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val items: List<UserResponse>,
)
