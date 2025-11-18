package com.travel.domain.service

import com.travel.helper.upload.ValidationResult

class UploadService(private val fileUploader: FileUploader) {
    suspend fun uploadFile(validationResult: ValidationResult.Valid): UploadResult {
        val url = fileUploader.upload(validationResult.bytes, validationResult.originalName)
        val fileName = url.substringAfterLast('/')
        return UploadResult(url, fileName)
    }
}

data class UploadResult(val url: String, val fileName: String)
