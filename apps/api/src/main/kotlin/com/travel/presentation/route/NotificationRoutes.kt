package com.travel.presentation.route

import com.travel.presentation.controller.NotificationController
import com.travel.presentation.model.CreateNotificationRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.notificationRoutes() {
    val notificationController by inject<NotificationController>()

    authenticate {
        route("/api/notifications") {
            get("/{userId}") {
                val userId = call.parameters["userId"]?.toLongOrNull()
                if (userId != null) {
                    val notifications = notificationController.getNotificationsByUser(userId)
                    call.respond(notifications)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }

            post("/{userId}") {
                val userId = call.parameters["userId"]?.toLongOrNull()
                if (userId != null) {
                    val request = call.receive<CreateNotificationRequest>()
                    val notification = notificationController.createNotification(userId, request)
                    call.respond(HttpStatusCode.Created, notification)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }

            patch("/{notificationId}/read") {
                val notificationId = call.parameters["notificationId"]?.toLongOrNull()
                if (notificationId != null) {
                    val result = notificationController.markAsRead(notificationId)
                    if (result) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid notification ID")
                }
            }

            delete("/{notificationId}") {
                val notificationId = call.parameters["notificationId"]?.toLongOrNull()
                if (notificationId != null) {
                    val result = notificationController.deleteNotification(notificationId)
                    if (result) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid notification ID")
                }
            }
        }
    }
}
