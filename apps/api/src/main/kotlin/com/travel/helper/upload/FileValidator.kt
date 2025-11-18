package com.travel.helper.upload

import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import java.util.Locale

object FileValidator {
    private val ALLOWED_EXTENSIONS = setOf("jpg", "jpeg", "png", "webp")
    private const val MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024 // 5MB

    fun validate(part: PartData.FileItem): ValidationResult {
        val fileName = part.originalFileName ?: return ValidationResult.Invalid("File name is missing.")
        val fileExtension = fileName.substringAfterLast('.', "").lowercase(Locale.getDefault())

        if (fileExtension !in ALLOWED_EXTENSIONS) {
            return ValidationResult.Invalid("Invalid file format. Allowed formats: ${ALLOWED_EXTENSIONS.joinToString()}.")
        }

        val fileBytes = part.streamProvider().readBytes()
        if (fileBytes.size > MAX_FILE_SIZE_BYTES) {
            return ValidationResult.Invalid("File is too large. Maximum size is 5MB.")
        }

        return ValidationResult.Valid(fileBytes, fileName, fileExtension)
    }
}

sealed class ValidationResult {
    data class Valid(val bytes: ByteArray, val originalName: String, val extension: String) : ValidationResult()

    data class Invalid(val message: String) : ValidationResult()
}
