package com.travel

import com.travel.core.Config
import com.travel.core.LanguagePlugin
import com.travel.plugins.*
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val dotenv = dotenv()
    System.setProperty("JWT_SECRET", dotenv["JWT_SECRET"])
    embeddedServer(Netty, port = dotenv["PORT"]?.toInt() ?: 8080, host = "0.0.0.0", module = Application::module).start(
        wait = true
    )
}

fun Application.module() {
    install(LanguagePlugin)
    configureDI()
    configureSecurity()
    configureAdministration()
    configureRateLimiting()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureDatabase()
    if (Config.appEnv == "development") {
        kotlinx.coroutines.runBlocking {
            com.travel.core.DevDataSeeder.seedHotelsIfEmpty()
            com.travel.core.DevDataSeeder.seedDestinationsIfEmpty()
            com.travel.core.DevDataSeeder.seedUsersIfEmpty()
//            com.travel.core.DevDataSeeder.seedBookingsIfEmpty()
            com.travel.core.DevDataSeeder.seedReviewsIfEmpty()
            com.travel.seeder.DevSeeder.seedAdminUser()
        }
    }
    configureValidation()
    configureRouting()
}
