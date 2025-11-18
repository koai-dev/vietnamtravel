package com.travel.data.model

import io.ktor.server.plugins.requestvalidation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class LocalFoodRequest(
    val destinationId: Long,
    val nameVi: String,
    val nameEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val images: List<String>,
    val tempUrlMap: Map<String, String>? = null,
)

fun LocalFoodRequest.validate(): ValidationResult {
    val errors = mutableListOf<String>()
    if (nameVi.isBlank()) {
        errors.add("Vietnamese name cannot be blank.")
    }
    return if (errors.isEmpty()) ValidationResult.Valid else ValidationResult.Invalid(errors)
}

@Serializable
data class LocalFoodResponse(
    val id: Long,
    val destinationId: Long,
    val nameVi: String,
    val nameEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val images: List<String>,
)
