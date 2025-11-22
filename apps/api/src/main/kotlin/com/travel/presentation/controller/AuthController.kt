package com.travel.presentation.controller

import com.travel.data.model.LoginRequest
import com.travel.data.model.RefreshTokenRequest
import com.travel.data.model.RegisterRequest
import com.travel.data.model.TokenResponse
import com.travel.data.table.UserRole
import com.travel.domain.model.User
import com.travel.domain.service.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class AuthController(
    private val authService: AuthService,
) : BaseController() {
    suspend fun register(
        call: ApplicationCall,
        request: RegisterRequest,
    ) {
        val user =
            User(
                email = request.email,
                passwordHash = request.password,
                name = request.name,
                avatarUrl = null,
                phone = null,
                role = UserRole.USER,
            )
        authService.register(user)
        respondWith(call, "User registered successfully")
    }

    suspend fun login(
        call: ApplicationCall,
        request: LoginRequest,
    ) {
        val tokenPair = authService.login(request.email, request.password)
        if (tokenPair != null) {
            respondWith(call, TokenResponse(tokenPair.accessToken, tokenPair.refreshToken))
        } else {
            respondWithError(call, "Invalid credentials", HttpStatusCode.Unauthorized)
        }
    }

    suspend fun refreshToken(
        call: ApplicationCall,
        request: RefreshTokenRequest,
    ) {
        val tokenPair = authService.refreshToken(request.refreshToken)
        if (tokenPair != null) {
            respondWith(call, TokenResponse(tokenPair.accessToken, tokenPair.refreshToken))
        } else {
            respondWithError(call, "Invalid refresh token", HttpStatusCode.Unauthorized)
        }
    }

    suspend fun logout(
        call: ApplicationCall,
        request: RefreshTokenRequest,
    ) {
        authService.logout(request.refreshToken)
        respondWith(call, "Logged out successfully")
    }
}
