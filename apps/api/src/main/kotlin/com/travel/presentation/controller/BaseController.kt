package com.travel.presentation.controller

import com.travel.core.ApiResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

open class BaseController {
    suspend inline fun <reified T : Any> respondWith(
        call: ApplicationCall,
        result: T,
        message: String = "Success",
    ) {
        call.respond(HttpStatusCode.OK, ApiResult.Success(result, message))
    }

    suspend fun respondWithError(
        call: ApplicationCall,
        message: String,
        code: HttpStatusCode = HttpStatusCode.BadRequest,
    ) {
        call.respond(code, ApiResult.Error(message, code.value))
    }
}
