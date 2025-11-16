package com.travel.presentation.route

import com.travel.data.model.UserTrackingDTO
import com.travel.core.RateLimiter
import com.travel.core.lang
import com.travel.domain.repository.RedisRepository
import com.travel.domain.service.UserTrackingRepository
import com.travel.presentation.controller.HotelController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hotelRoutes() {
    val hotelController by inject<HotelController>()
    val redisRepository by inject<RedisRepository>()
    val userTrackingRepository by inject<UserTrackingRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/hotels") {
        install(rateLimiter.limit("/hotels", 100, 60))
        get {
            val city = call.request.queryParameters["city"]
            val sort = call.request.queryParameters["sort"]
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val lang = call.lang()
            val hotels = hotelController.getAll(city, sort, page, lang)
            call.respond(hotels)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                val hotel = hotelController.getById(id, lang)
                if (hotel != null) {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("userId")?.asLong()
                    val userAgent = call.request.headers["User-Agent"]
                    val platform = if (userAgent?.contains("Android") == true) "Android" else if (userAgent?.contains(
                            "iPhone"
                        ) == true
                    ) "iOS" else "Web"

                    val os = userAgent?.let {
                        val osRegex = Regex("(Windows NT \\d+\\.\\d+|Android \\d+\\.\\d+|iPhone OS \\d+_\\d+_\\d+|Mac OS X \\d+_\\d+_\\d+)")
                        osRegex.find(it)?.value
                    }
                    val osVersion = os?.split(" ")?.get(1)

                    userTrackingRepository.add(
                        UserTrackingDTO(
                            userId = userId,
                            device = userAgent,
                            platform = platform,
                            endpoint = call.request.local.uri,
                            os = os,
                            osVersion = osVersion,
                            ipAddress = call.request.local.remoteHost
                        )
                    )
                    call.respond(hotel)
                } else {
                    call.respondText("Hotel not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
