package com.travel.core

import io.ktor.http.HttpStatusCode

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: HttpStatusCode) : ApiResult<Nothing>()
}
