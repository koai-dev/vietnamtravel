package com.travel.presentation.route

import com.travel.core.RateLimitException
import com.travel.data.model.CreateBookingRequest
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.BookingController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.bookingRoutes() {
    val bookingController by inject<BookingController>()
    val redisRepository by inject<RedisRepository>()

    authenticate {
        route("/api/bookings") {
            post {
                val principal = call.principal<JWTPrincipal>()
                if (principal != null) {
                    val userId = principal.payload.getClaim("userId").asLong()
                    val key = "rate:booking:$userId"
                    val count = redisRepository.incr(key)
                    if (count == 1L) {
                        redisRepository.expire(key, 60)
                    }
                    if (count > 3) {
                        throw RateLimitException()
                    }

                    val request = call.receive<CreateBookingRequest>()
                    val booking = bookingController.createBooking(userId, request)
                    call.respond(booking)
                }
            }
        }
    }
}
