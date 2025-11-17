package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.data.model.CreateUserRequest
import com.travel.data.model.UpdateUserRequest
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.UserController
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userController by inject<UserController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    authenticate {
        route("/api/users") {
            get {
                val query = call.request.queryParameters["q"]
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull() ?: 10
                userController.getUsers(call, query, page, pageSize)
            }
            post {
                val request = call.receive<CreateUserRequest>()
                userController.createUser(call, request)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
                userController.deleteUser(call, id)
            }
        }
        route("/api/users/me") {
            install(rateLimiter.limit("/users/me", 100, 60))
            get {
                val principal = call.principal<JWTPrincipal>()
                if (principal != null) {
                    userController.getMe(call, principal)
                }
            }
            put {
                val principal = call.principal<JWTPrincipal>()
                if (principal != null) {
                    val request = call.receive<UpdateUserRequest>()
                    userController.updateMe(call, principal, request)
                }
            }
        }
    }
}
