package com.travel.presentation.route

import com.travel.presentation.controller.DashboardController
import com.travel.presentation.controller.TrackingController
import com.travel.presentation.controller.UserController
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.dashboardRoutes() {
    val trackingController by inject<TrackingController>()
    val userController by inject<UserController>()
    val dashboardController by inject<DashboardController>()

    route("/api/dashboard") {
        get("/tracking/summary") {
            val range = call.request.queryParameters["range"] ?: "day"
            val date = call.request.queryParameters["date"]
            val summary = trackingController.getTrackingSummary(range, date)
            call.respond(summary)
        }

        get("/users/new") {
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 20
            val offset = call.request.queryParameters["offset"]?.toIntOrNull() ?: 0
            val users = userController.getNewUsers(call, limit, offset)
            call.respond(users)
        }
        get("/stats") {
            dashboardController.getDashboardStats(call)
        }
    }
}
