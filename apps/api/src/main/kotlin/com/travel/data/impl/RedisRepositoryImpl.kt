package com.travel.data.impl

import com.travel.core.Config
import com.travel.domain.repository.RedisRepository
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.params.SetParams

class RedisRepositoryImpl : RedisRepository {
    private val pool = JedisPool(JedisPoolConfig(), Config.redisHost, Config.redisPort)

    override fun get(key: String): String? {
        return pool.resource.use { jedis ->
            jedis.get(key)
        }
    }

    override fun setex(
        key: String,
        seconds: Int,
        value: String,
    ) {
        pool.resource.use { jedis ->
            jedis.setex(key, seconds.toLong(), value)
        }
    }

    override fun del(key: String) {
        pool.resource.use { jedis ->
            jedis.del(key)
        }
    }

    override fun incr(key: String): Long {
        return pool.resource.use { jedis ->
            jedis.incr(key)
        }
    }

    override fun expire(
        key: String,
        seconds: Int,
    ) {
        pool.resource.use { jedis ->
            jedis.expire(key, seconds.toLong())
        }
    }

    override fun setnx(
        key: String,
        value: String,
        seconds: Long,
    ): Boolean {
        return pool.resource.use { jedis ->
            jedis.set(key, value, SetParams().nx().ex(seconds)) == "OK"
        }
    }
}
