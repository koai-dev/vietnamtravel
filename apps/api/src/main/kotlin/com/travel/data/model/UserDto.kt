package com.travel.data.model

import com.travel.data.table.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String?,
    val avatarUrl: String?,
    val phone: String?,
    val role: UserRole,
)

@Serializable
data class CreateUserRequest(
    val email: String,
    val name: String,
    val phone: String?,
    val role: UserRole,
)

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val avatarUrl: String? = null,
    val phone: String? = null,
)
