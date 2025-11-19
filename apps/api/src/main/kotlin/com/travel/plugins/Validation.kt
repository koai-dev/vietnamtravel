package com.travel.plugins

import com.travel.core.ApiResult
import com.travel.core.RateLimitException
import com.travel.data.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<CreateBookingRequest> { it.validate() }
        validate<HotelRequest> { it.validate() }
        validate<RatingUpdateRequest> { it.validate() }
        validate<LocalFoodRequest> { it.validate() }
        validate<RestaurantRequest> { it.validate() }
        validate<CreateUserRequest> { it.validate() }
        validate<UpdateUserRequest> { it.validate() }
        validate<RegisterRequest> { it.validate() }
        validate<LoginRequest> { it.validate() }
        validate<RefreshTokenRequest> { it.validate() }
    }
    install(StatusPages) {
        exception<RateLimitException> { call, cause ->
            call.respond(HttpStatusCode.TooManyRequests, "Rate limit exceeded")
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ApiResult.Error(cause.reasons.joinToString()))
        }
    }
}
