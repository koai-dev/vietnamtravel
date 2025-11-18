package com.travel.plugins

import com.travel.core.ApiResult
import com.travel.data.model.CreateBookingRequest
import com.travel.data.model.CreateUserRequest
import com.travel.data.model.HotelRequest
import com.travel.data.model.LocalFoodRequest
import com.travel.data.model.LoginRequest
import com.travel.data.model.RatingUpdateRequest
import com.travel.data.model.RefreshTokenRequest
import com.travel.data.model.RegisterRequest
import com.travel.data.model.RestaurantRequest
import com.travel.data.model.UpdateUserRequest
import com.travel.data.model.validate
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
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ApiResult.Error(cause.reasons.joinToString()))
        }
    }
}
