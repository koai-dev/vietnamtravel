package com.travel.plugins

import com.travel.core.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory

fun Application.configureDatabase() {
    val logger = LoggerFactory.getLogger("Database")
    val isDevelopment = Config.appEnv == "development"

    val hikariConfig = if (isDevelopment) {
        HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = Config.dbH2Url
            username = Config.dbH2User
            password = Config.dbH2Password
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
    } else {
        HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = Config.dbUrl
            username = Config.dbUser
            password = Config.dbPassword
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
    }

    if (!isDevelopment) {
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
    } else {
        logger.info("Skipping Flyway migration for H2 database")
    }
}
