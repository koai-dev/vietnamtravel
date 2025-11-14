package com.example.presentation

import com.example.application.LoginRequest
import com.example.application.RefreshTokenRequest
import com.example.application.RegisterRequest
import com.example.core.RateLimiter
import com.example.domain.RedisRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authController by inject<AuthController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/auth") {
        install(rateLimiter.limit("/auth", 100, 60))
        post("/register") {
            val request = call.receive<RegisterRequest>()
            authController.register(request)
            call.respondText("User registered successfully")
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val key = "rate:login:${request.email}"
            val count = redisRepository.incr(key)
            if (count == 1L) {
                redisRepository.expire(key, 5 * 60)
            }
            if (count > 10) {
                throw com.example.core.RateLimitException()
            }
            val tokenResponse = authController.login(request)
            if (tokenResponse != null) {
                call.respond(tokenResponse)
            } else {
                call.respondText("Invalid credentials", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }

        post("/refresh") {
            val request = call.receive<RefreshTokenRequest>()
            val tokenResponse = authController.refreshToken(request)
            if (tokenResponse != null) {
                call.respond(tokenResponse)
            } else {
                call.respondText("Invalid refresh token", status = io.ktor.http.HttpStatusCode.Unauthorized)
            }
        }

        post("/logout") {
            val request = call.receive<RefreshTokenRequest>()
            authController.logout(request)
            call.respondText("Logged out successfully")
        }
    }
}
