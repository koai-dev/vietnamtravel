package com.travel.core

import io.ktor.http.*
import kotlinx.serialization.Serializable

sealed class ApiResult<T> {
    @Serializable
    data class Success<T>(val data: T, val message: String = "Success") : ApiResult<T>()

    @Serializable
    data class Error(val message: String, val code: Int = HttpStatusCode.BadRequest.value) : ApiResult<Nothing>()
}
