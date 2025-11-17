package com.travel.core

import com.travel.domain.repository.RedisRepository
import java.util.*

suspend fun <T> distributedLock(
    redis: RedisRepository,
    lockKey: String,
    expire: Int,
    block: suspend () -> T,
): T {
    val lockValue = UUID.randomUUID().toString()
    val acquired = redis.setnx(lockKey, lockValue, expire.toLong())

    if (acquired) {
        try {
            return block()
        } finally {
            redis.del(lockKey) // Basic unlock
        }
    } else {
        throw LockNotAcquiredException("Could not acquire lock for key: $lockKey")
    }
}

class LockNotAcquiredException(message: String) : RuntimeException(message)

fun RedisRepository.setnx(
    key: String,
    value: String,
    seconds: Long,
): Boolean {
    // This is a simplified implementation. A real implementation would use a Lua script.
    val result = get(key)
    if (result == null) {
        setex(key, seconds.toInt(), value)
        return true
    }
    return false
}
