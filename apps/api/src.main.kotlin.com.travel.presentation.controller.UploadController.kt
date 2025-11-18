package com.travel.presentation.controller

import com.travel.domain.service.UploadService
import com.travel.helper.upload.FileValidator
import com.travel.helper.upload.ValidationResult
import com.travel.presentation.base.BaseController
import com.travel.presentation.base.BaseResponse
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UploadController(private val uploadService: UploadService) : BaseController() {

    suspend fun upload(call: ApplicationCall) {
        val multipart = call.receiveMultipart()
        var fileValidationResult: ValidationResult? = null

        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                fileValidationResult = FileValidator.validate(part)
            }
            part.dispose()
        }

        when (val result = fileValidationResult) {
            is ValidationResult.Valid -> {
                val uploadResult = uploadService.uploadFile(result)
                call.respond(BaseResponse.success(uploadResult))
            }
            is ValidationResult.Invalid -> {
                call.respond(BaseResponse.error(result.message))
            }
            null -> {
                call.respond(BaseResponse.error("No file part found in the request."))
            }
        }
    }
}
