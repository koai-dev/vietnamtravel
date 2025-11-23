package com.travel.presentation.controller

import com.travel.data.mapper.toUserResponse
import com.travel.data.model.CreateUserRequest
import com.travel.data.model.UpdateUserRequest
import com.travel.data.table.UserRole
import com.travel.domain.model.User
import com.travel.domain.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal

class UserController(
    private val userService: UserService,
) : BaseController() {
    suspend fun getUsers(
        call: ApplicationCall,
        query: String?,
        page: Int,
        pageSize: Int,
    ) {
        val (users, total) = userService.getUsers(query, page, pageSize)
        val response = users.map { it.toUserResponse() }

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = response,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
    }

    suspend fun getMe(
        call: ApplicationCall,
        principal: JWTPrincipal,
    ) {
        val userId = principal.payload.getClaim("userId").asLong()
        val user = userService.getUser(userId)?.toUserResponse()
        if (user != null) {
            respondWith(call, user)
        } else {
            respondWithError(call, "User not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun createUser(
        call: ApplicationCall,
        request: CreateUserRequest,
    ) {
        val user =
            User(
                email = request.email,
                passwordHash = "password_placeholder", // Will be properly hashed in the service
                name = request.name,
                avatarUrl = null,
                phone = request.phone,
                role = UserRole.valueOf(request.role.uppercase()),
            )
        val createdUser = userService.createUser(user).toUserResponse()
        respondWith(call, createdUser)
    }

    suspend fun updateMe(
        call: ApplicationCall,
        principal: JWTPrincipal,
        request: UpdateUserRequest,
    ) {
        val userId = principal.payload.getClaim("userId").asLong()
        val updatedUser = userService.updateUser(userId, request.name, request.avatarUrl, request.phone)?.toUserResponse()
        if (updatedUser != null) {
            respondWith(call, updatedUser)
        } else {
            respondWithError(call, "User not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun deleteUser(
        call: ApplicationCall,
        id: Long,
    ) {
        userService.deleteUser(id)
        respondWith(call, true, "User deleted successfully")
    }

    suspend fun getNewUsers(
        call: ApplicationCall,
        limit: Int,
        offset: Int,
    ) {
        val newUsers = userService.getNewUsers(limit, offset)
        respondWith(call, newUsers)
    }
}
