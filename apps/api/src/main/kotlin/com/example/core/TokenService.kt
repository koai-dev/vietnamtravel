package com.example.core

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.core.Config
import java.util.*

data class TokenPair(val accessToken: String, val refreshToken: String)

object TokenService {
    private val secret = Config.jwtSecret
    private val issuer = Config.dotenv["JWT_ISSUER"] ?: "com.example"
    private val audience = Config.dotenv["JWT_AUDIENCE"] ?: "users"
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT.require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun generateTokenPair(userId: Long, jti: String): TokenPair {
        val accessToken = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .withJWTId(jti)
            .withExpiresAt(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
            .sign(algorithm)

        return TokenPair(accessToken, refreshToken)
    }

    fun decode(token: String): DecodedJWT = verifier.verify(token)
}
