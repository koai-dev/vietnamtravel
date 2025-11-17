package com.travel.domain.model

import com.travel.data.table.UserRole

data class User(
    val id: Long = 0,
    val email: String,
    val passwordHash: String,
    val name: String?,
    val avatarUrl: String?,
    val phone: String?,
    val role: UserRole,
)
