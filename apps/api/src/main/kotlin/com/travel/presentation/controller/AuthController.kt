package com.travel.presentation.controller

import com.travel.data.model.LoginRequest
import com.travel.data.model.RefreshTokenRequest
import com.travel.data.model.RegisterRequest
import com.travel.data.model.TokenResponse
import com.travel.data.table.UserRole
import com.travel.domain.model.User
import com.travel.domain.service.AuthService

class AuthController(
    private val authService: AuthService,
) {
    suspend fun register(request: RegisterRequest) {
        val user =
            User(
                email = request.email,
                passwordHash = request.password,
                name = request.name,
                avatarUrl = null,
                phone = null,
                role = UserRole.user,
            )
        authService.register(user)
    }

    suspend fun login(request: LoginRequest): TokenResponse? {
        val tokenPair = authService.login(request.email, request.password)
        return tokenPair?.let { TokenResponse(it.accessToken, it.refreshToken) }
    }

    suspend fun refreshToken(request: RefreshTokenRequest): TokenResponse? {
        val tokenPair = authService.refreshToken(request.refreshToken)
        return tokenPair?.let { TokenResponse(it.accessToken, it.refreshToken) }
    }

    suspend fun logout(request: RefreshTokenRequest) {
        authService.logout(request.refreshToken)
    }
}
