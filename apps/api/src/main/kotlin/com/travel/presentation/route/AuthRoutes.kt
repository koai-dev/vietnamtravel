package com.travel.presentation.route

import com.travel.core.RateLimitException
import com.travel.core.RateLimiter
import com.travel.data.model.LoginRequest
import com.travel.data.model.RefreshTokenRequest
import com.travel.data.model.RegisterRequest
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.AuthController
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authController by inject<AuthController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/auth") {
        install(rateLimiter.limit("/auth", 100, 60))
        post("/register") {
            val request = call.receive<RegisterRequest>()
            authController.register(call, request)
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val key = "rate:login:${request.email}"
            val count = redisRepository.incr(key)
            if (count == 1L) {
                redisRepository.expire(key, 5 * 60)
            }
            if (count > 10) {
                throw RateLimitException()
            }
            authController.login(call, request)
        }

        post("/refresh") {
            val request = call.receive<RefreshTokenRequest>()
            authController.refreshToken(call, request)
        }

        post("/logout") {
            val request = call.receive<RefreshTokenRequest>()
            authController.logout(call, request)
        }
    }
}
