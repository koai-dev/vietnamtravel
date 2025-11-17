package com.travel.domain.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.travel.core.TokenPair
import com.travel.core.TokenService
import com.travel.domain.model.User
import com.travel.domain.repository.AuthRepository
import com.travel.domain.repository.RedisRepository
import java.util.UUID

class AuthService(
    private val authRepository: AuthRepository,
    private val redisRepository: RedisRepository,
) {
    suspend fun register(user: User): User {
        // TODO: add validation
        val hashedPassword = BCrypt.withDefaults().hashToString(12, user.passwordHash.toCharArray())
        return authRepository.saveUser(user.copy(passwordHash = hashedPassword))
    }

    suspend fun login(
        email: String,
        password: String,
    ): TokenPair? {
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
