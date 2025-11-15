package com.example.plugins

import com.example.core.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory
import java.sql.Connection

fun Application.configureDatabase() {
    val logger = LoggerFactory.getLogger("Database")
    val hikariConfig = HikariConfig().apply {
        driverClassName = "com.mysql.cj.jdbc.Driver"
        jdbcUrl = Config.dbUrl
        username = Config.dbUser
        password = Config.dbPassword
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    // Run Flyway migrations
    val flyway = Flyway.configure().dataSource(dataSource).load()
    try {
        flyway.migrate()
        logger.info("Flyway migration successful")
    } catch (e: Exception) {
        logger.error("Flyway migration failed", e)
        throw e
    }
}
