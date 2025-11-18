package com.travel.presentation.route

import com.travel.presentation.controller.UploadController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.uploadRoutes() {
    val uploadController by inject<UploadController>()

    route("/api/uploads") {
        post {
            uploadController.upload(context)
        }
    }
}
