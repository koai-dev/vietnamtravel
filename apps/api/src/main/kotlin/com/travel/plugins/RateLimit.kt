package com.travel.plugins

import com.travel.core.RateLimiter
import com.travel.domain.repository.RedisRepository
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRateLimiting() {
    val redisRepository by inject<RedisRepository>()
    RateLimiter(redisRepository)
}
