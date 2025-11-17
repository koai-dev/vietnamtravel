package com.travel.core

import io.ktor.http.HttpStatusCode

sealed class ApiResult<T> {
    data class Success<T>(val data: T, val message: String = "Success") : ApiResult<T>()

    data class Error(val message: String, val code: HttpStatusCode = HttpStatusCode.BadRequest) : ApiResult<Nothing>()
}
