package com.example.plugins

import com.example.presentation.authRoutes
import com.example.presentation.destinationRoutes
import com.example.presentation.hotelRoutes
import com.example.presentation.bookingRoutes
import com.example.presentation.tourRoutes
import com.example.presentation.userRoutes
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
    }
}
