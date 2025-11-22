package com.travel.presentation.route

import com.travel.data.model.CreateNotificationRequest
import com.travel.presentation.controller.NotificationController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.notificationRoutes() {
    val notificationController by inject<NotificationController>()

    authenticate {
        route("/api/notifications") {
            get("/{userId}") {
                val userId = call.parameters["userId"]?.toLongOrNull()
                if (userId != null) {
                    notificationController.getNotificationsByUser(call, userId)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }

            post("/{userId}") {
                val userId = call.parameters["userId"]?.toLongOrNull()
                if (userId != null) {
                    val request = call.receive<CreateNotificationRequest>()
                    notificationController.createNotification(call, userId, request)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }

            patch("/{notificationId}/read") {
                val notificationId = call.parameters["notificationId"]?.toLongOrNull()
                if (notificationId != null) {
                    notificationController.markAsRead(call, notificationId)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid notification ID")
                }
            }

            delete("/{notificationId}") {
                val notificationId = call.parameters["notificationId"]?.toLongOrNull()
                if (notificationId != null) {
                    notificationController.deleteNotification(call, notificationId)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid notification ID")
                }
            }
        }
    }
}
