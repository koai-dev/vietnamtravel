package com.travel.plugins

import com.travel.core.Config
import com.travel.data.table.Bookings
import com.travel.data.table.Destinations
import com.travel.data.table.Favorites
import com.travel.data.table.HotelImages
import com.travel.data.table.Hotels
import com.travel.data.table.LocalFoods
import com.travel.data.table.Notifications
import com.travel.data.table.RestaurantLocalFoods
import com.travel.data.table.Restaurants
import com.travel.data.table.Reviews
import com.travel.data.table.Rooms
import com.travel.data.table.Tours
import com.travel.data.table.UserTracking
import com.travel.data.table.Users
import com.travel.domain.model.Booking
import com.travel.seeder.DevSeeder
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Application.configureDatabase() {
    val logger = LoggerFactory.getLogger("Database")
    val isDevelopment = Config.appEnv == "development"
    val devSeeder by inject<DevSeeder>()

    if (!isDevelopment) {
        // Run Flyway migrations
        val hikariConfig =
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

        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)
        val flyway = Flyway.configure().dataSource(dataSource).load()
        try {
            flyway.migrate()
            logger.info("Flyway migration successful")
        } catch (e: Exception) {
            logger.error("Flyway migration failed", e)
            throw e
        }
    } else {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL;DATABASE_TO_UPPER=false"

        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = "sa",
            password = ""
        )

        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Bookings)
            SchemaUtils.create(Destinations)
            SchemaUtils.create(HotelImages)
            SchemaUtils.create(Favorites)
            SchemaUtils.create(Hotels)
            SchemaUtils.create(LocalFoods)
            SchemaUtils.create(Notifications)
            SchemaUtils.create(Restaurants)
            SchemaUtils.create(RestaurantLocalFoods)
            SchemaUtils.create(Reviews)
            SchemaUtils.create(Rooms)
            SchemaUtils.create(Tours)
            SchemaUtils.create(UserTracking)
        }

    }
}
