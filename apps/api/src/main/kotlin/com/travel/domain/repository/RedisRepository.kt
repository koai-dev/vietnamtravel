package com.travel.domain.repository

interface RedisRepository {
    fun get(key: String): String?

    fun setex(
        key: String,
        seconds: Int,
        value: String,
    )

    fun del(key: String)

    fun incr(key: String): Long

    fun expire(
        key: String,
        seconds: Int,
    )

    fun setnx(
        key: String,
        value: String,
        seconds: Long,
    ): Boolean
}
