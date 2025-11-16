package com.travel.presentation.route

import com.travel.data.model.CreateUserRequest
import com.travel.data.model.UpdateUserRequest
import com.travel.core.RateLimiter
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.UserController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userController by inject<UserController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    authenticate {
        route("/users") {
            get {
                val query = call.request.queryParameters["q"]
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull() ?: 10
                val users = userController.getUsers(query, page, pageSize)
                call.respond(users)
            }
            post {
                val request = call.receive<CreateUserRequest>()
                val user = userController.createUser(request)
                call.respond(user)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id != null) {
                    userController.deleteUser(id)
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
        route("/users/me") {
            install(rateLimiter.limit("/users/me", 100, 60))
            get {
                val principal = call.principal<JWTPrincipal>()
                if (principal != null) {
                    val user = userController.getMe(principal)
                    if (user != null) {
                        call.respond(user)
                    } else {
                        call.respondText("User not found", status = HttpStatusCode.NotFound)
                    }
                }
            }
            put {
                val principal = call.principal<JWTPrincipal>()
                if (principal != null) {
                    val request = call.receive<UpdateUserRequest>()
                    val user = userController.updateMe(principal, request)
                    if (user != null) {
                        call.respond(user)
                    } else {
                        call.respondText("User not found", status = HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}
