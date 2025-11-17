package com.travel.data.model

import com.travel.data.table.UserRole
import io.ktor.server.plugins.requestvalidation.ValidationResult
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

fun CreateUserRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (email.isBlank() || !email.contains("@")) {
        errors.add("Invalid email address.")
    }
    if (name.isBlank()) {
        errors.add("Name cannot be blank.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val avatarUrl: String? = null,
    val phone: String? = null,
)

fun UpdateUserRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    name?.let {
        if (it.isBlank()) {
            errors.add("Name cannot be blank.")
        }
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}
