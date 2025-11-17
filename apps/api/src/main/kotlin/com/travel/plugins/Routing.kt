package com.travel.plugins

import com.travel.presentation.route.*
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
        localFoodRoutes()
        restaurantRoutes()
        notificationRoutes()
    }
}
