package com.travel.core

import com.travel.domain.repository.RedisRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> cache(
    redis: RedisRepository,
    key: String,
    expire: Int,
    force: Boolean = false,
    block: suspend () -> T,
): T {
    if (!force) {
        val cached = redis.get(key)
        if (cached != null) {
            return Json.decodeFromString(cached)
        }
    }

    val result = block()
    redis.setex(key, expire, Json.encodeToString(result))
    return result
}
