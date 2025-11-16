package com.travel.core

import com.travel.domain.repository.RedisRepository
import io.ktor.server.application.*

class RateLimiter(private val redis: RedisRepository) {
    fun limit(route: String, limit: Int, duration: Int): RouteScopedPlugin<Unit> = createRouteScopedPlugin("RateLimit") {
        onCall { call ->
            val ip = call.request.local.remoteHost
            val key = "rate:$route:$ip"
            val count = redis.incr(key)
            if (count == 1L) {
                redis.expire(key, duration)
            }
            if (count > limit) {
                throw RateLimitException()
            }
        }
    }
}

class RateLimitException : RuntimeException("Rate limit exceeded")
