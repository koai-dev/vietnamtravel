package com.travel.presentation.route

import com.travel.presentation.controller.UploadController
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.uploadRoutes() {
    val uploadController by inject<UploadController>()

    route("/api/uploads") {
        post {
            uploadController.upload(call)
        }
    }
}
