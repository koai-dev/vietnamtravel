package com.travel.presentation.route

import com.travel.presentation.controller.TrackingController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.trackingRoutes() {
    val trackingController by inject<TrackingController>()

    route("/tracking") {
        get("/stats") {
            val period = call.request.queryParameters["period"]
            val startDate = call.request.queryParameters["startDate"]
            val endDate = call.request.queryParameters["endDate"]
            val stats = trackingController.getStats(period, startDate, endDate)
            call.respond(stats)
        }
    }
}
