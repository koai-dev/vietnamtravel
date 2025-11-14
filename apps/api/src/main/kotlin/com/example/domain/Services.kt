package com.example.domain

import com.example.core.TokenPair
import com.example.core.TokenService
import com.example.data.UserRole
import at.favre.lib.crypto.bcrypt.BCrypt
import java.util.UUID

class AuthService(
    private val authRepository: AuthRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun register(user: User): User {
        // TODO: add validation
        val hashedPassword = BCrypt.withDefaults().hashToString(12, user.passwordHash.toCharArray())
        return authRepository.saveUser(user.copy(passwordHash = hashedPassword))
    }

    suspend fun login(email: String, password: String):TokenPair? {
        val user = authRepository.findUserByEmail(email)
        if (user != null && BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash).verified) {
            val jti = UUID.randomUUID().toString()
            val tokenPair = TokenService.generateTokenPair(user.id, jti)
            redisRepository.setex("refresh_token:$jti", 7 * 24 * 60 * 60, user.id.toString())
            return tokenPair
        }
        return null
    }

    suspend fun refreshToken(refreshToken: String): TokenPair? {
        val decoded = TokenService.decode(refreshToken)
        val jti = decoded.id
        val userId = decoded.getClaim("userId").asLong()
        val storedUserId = redisRepository.get("refresh_token:$jti")
        if (storedUserId == userId.toString()) {
            redisRepository.del("refresh_token:$jti")
            val newJti = UUID.randomUUID().toString()
            val tokenPair = TokenService.generateTokenPair(userId, newJti)
            redisRepository.setex("refresh_token:$newJti", 7 * 24 * 60 * 60, userId.toString())
            return tokenPair
        }
        return null
    }

    suspend fun logout(refreshToken: String) {
        val decoded = TokenService.decode(refreshToken)
        val jti = decoded.id
        redisRepository.del("refresh_token:$jti")
    }
}

class UserService(private val userRepository: UserRepository) {
    suspend fun getUser(id: Long): User? = userRepository.findById(id)
    suspend fun updateUser(id: Long, name: String?, avatarUrl: String?, phone: String?): User? {
        return userRepository.updateUser(id, name, avatarUrl, phone)
    }
}

class DestinationService(
    private val destinationRepository: DestinationRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(lang: String): List<Destination> {
        val key = "destinations:all:$lang"
        return com.example.core.cache(redisRepository, key, 30 * 60) {
            destinationRepository.getAll()
        }
    }
    suspend fun getById(id: Long): Destination? = destinationRepository.findById(id)
}

class TourService(
    private val tourRepository: TourRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(): List<Tour> = tourRepository.getAll()
    suspend fun getById(id: Long): Tour? = tourRepository.findById(id)
    suspend fun getPopular(lang: String): List<Tour> {
        val key = "tours:popular:$lang"
        return com.example.core.cache(redisRepository, key, 10 * 60) {
            tourRepository.getPopular()
        }
    }
}

class HotelService(
    private val hotelRepository: HotelRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(city: String?, sort: String?, page: Int, lang: String): List<Hotel> {
        val key = "hotels:list:${city ?: "all"}:${sort ?: "none"}:$page:$lang"
        return com.example.core.cache(redisRepository, key, 5 * 60) {
            hotelRepository.getAll(city, sort, page)
        }
    }
    suspend fun getById(id: Long): Hotel? = hotelRepository.findById(id)
}

interface RedisRepository {
    fun get(key: String): String?
    fun setex(key: String, seconds: Int, value: String)
    fun del(key: String)
    fun incr(key: String): Long
    fun expire(key: String, seconds: Int)
    fun setnx(key: String, value: String, seconds: Long): Boolean
}
