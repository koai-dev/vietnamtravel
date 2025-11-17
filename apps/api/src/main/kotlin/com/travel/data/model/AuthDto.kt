package com.travel.data.model

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
)

fun RegisterRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (email.isBlank() || !email.contains("@")) {
        errors.add("Invalid email address.")
    }
    if (password.isBlank() || password.length < 8) {
        errors.add("Password must be at least 8 characters long.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

fun LoginRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (email.isBlank() || !email.contains("@")) {
        errors.add("Invalid email address.")
    }
    if (password.isBlank()) {
        errors.add("Password cannot be blank.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)

fun RefreshTokenRequest.validate(): ValidationResult {
    return if (refreshToken.isNotBlank()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid("Refresh token cannot be blank.")
    }
}

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
