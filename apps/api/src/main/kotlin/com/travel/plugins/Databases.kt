package com.travel.plugins

import com.travel.core.Config
import com.travel.seeder.DevSeeder
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.exception.FlywayValidateException
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Application.configureDatabase() {
    val logger = LoggerFactory.getLogger("Database")
    val isDevelopment = Config.appEnv == "development"
    val devSeeder by inject<DevSeeder>()
    // Run Flyway migrations
    val hikariConfig =
        HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = if (!isDevelopment) Config.dbUrl else "jdbc:mysql://localhost:3306/travel_db"
            username = if (!isDevelopment) Config.dbUser else "root"
            password = if (!isDevelopment) Config.dbPassword else ""
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)
    val flyway = Flyway.configure().dataSource(dataSource).load()
    try {
        flyway.migrate()
        logger.info("Flyway migration successful")
    } catch (e: FlywayValidateException) {
        if (isDevelopment) {
            logger.warn("Flyway validation failed, attempting repair", e)
            flyway.repair()
            flyway.migrate()
            logger.info("Flyway migration successful after repair")
        } else {
            throw e
        }
    } catch (e: Exception) {
        logger.error("Flyway migration failed", e)
        throw e
    }
}
