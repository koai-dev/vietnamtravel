package com.example.plugins

import com.example.core.RateLimitException
import com.example.core.RateLimiter
import com.example.domain.RedisRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureRateLimiting() {
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    install(StatusPages) {
        exception<RateLimitException> { call, cause ->
            call.respond(HttpStatusCode.TooManyRequests, "Rate limit exceeded")
        }
    }

    // This is a placeholder for where the rate limit would be applied globally.
    // However, since the requirement is to apply it to all routes, and Ktor
    // doesn't have a simple way to do this for all routes at once, I will
    // apply it to the individual route files.
}
