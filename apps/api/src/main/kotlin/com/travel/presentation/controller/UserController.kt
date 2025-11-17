package com.travel.presentation.controller

import com.travel.data.mapper.toUserResponse
import com.travel.data.model.CreateUserRequest
import com.travel.data.model.NewUsersResponse
import com.travel.data.model.UpdateUserRequest
import com.travel.data.model.UserResponse
import com.travel.domain.model.User
import com.travel.domain.service.UserService
import io.ktor.server.auth.jwt.*

class UserController(
    private val userService: UserService,
) {
    suspend fun getUsers(
        query: String?,
        page: Int,
        pageSize: Int,
    ): List<UserResponse> {
        return userService.getUsers(query, page, pageSize).map { it.toUserResponse() }
    }

    suspend fun getMe(principal: JWTPrincipal): UserResponse? {
        val userId = principal.payload.getClaim("userId").asLong()
        return userService.getUser(userId)?.toUserResponse()
    }

    suspend fun createUser(request: CreateUserRequest): UserResponse {
        val user =
            User(
                email = request.email,
                passwordHash = "password_placeholder", // Will be properly hashed in the service
                name = request.name,
                avatarUrl = null,
                phone = request.phone,
                role = request.role,
            )
        return userService.createUser(user).toUserResponse()
    }

    suspend fun updateMe(
        principal: JWTPrincipal,
        request: UpdateUserRequest,
    ): UserResponse? {
        val userId = principal.payload.getClaim("userId").asLong()
        return userService.updateUser(userId, request.name, request.avatarUrl, request.phone)?.toUserResponse()
    }

    suspend fun deleteUser(id: Long) {
        userService.deleteUser(id)
    }

    suspend fun getNewUsers(
        limit: Int,
        offset: Int,
    ): NewUsersResponse {
        return userService.getNewUsers(limit, offset)
    }
}
