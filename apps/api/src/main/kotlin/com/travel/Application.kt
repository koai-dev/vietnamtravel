package com.travel

import com.travel.core.LanguagePlugin
import com.travel.plugins.configureAdministration
import com.travel.plugins.configureDI
import com.travel.plugins.configureDatabase
import com.travel.plugins.configureHTTP
import com.travel.plugins.configureMonitoring
import com.travel.plugins.configureRateLimiting
import com.travel.plugins.configureRouting
import com.travel.plugins.configureSecurity
import com.travel.plugins.configureSerialization
import com.travel.plugins.configureValidation
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val dotenv = dotenv()
    System.setProperty("JWT_SECRET", dotenv["JWT_SECRET"])
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
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
    if (environment.developmentMode) {
        kotlinx.coroutines.runBlocking {
            com.travel.core.DevDataSeeder.seedHotelsIfEmpty()
            com.travel.seeder.DevSeeder.seedAdminUser()
        }
    }
    configureValidation()
    configureRouting()
}
