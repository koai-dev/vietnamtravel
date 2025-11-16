package com.travel.plugins

import com.travel.presentation.route.authRoutes
import com.travel.presentation.route.destinationRoutes
import com.travel.presentation.route.hotelRoutes
import com.travel.presentation.route.bookingRoutes
import com.travel.presentation.route.tourRoutes
import com.travel.presentation.route.dashboardRoutes
import com.travel.presentation.route.trackingRoutes
import com.travel.presentation.route.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRoutes()
        userRoutes()
        destinationRoutes()
        tourRoutes()
        hotelRoutes()
        bookingRoutes()
        trackingRoutes()
        dashboardRoutes()
    }
}
