package com.example

import com.example.plugins.configureDI
import com.example.plugins.configureDatabase
import com.example.plugins.configureHTTP
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.core.LanguagePlugin
import com.example.plugins.configureRateLimiting
import com.example.plugins.configureSerialization
import com.example.plugins.configureAdministration
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(LanguagePlugin)
    configureDI()
    configureDatabase()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureHTTP()
    configureRouting()
    configureAdministration()
    configureRateLimiting()
}
