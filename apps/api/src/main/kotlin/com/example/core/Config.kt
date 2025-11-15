package com.example.core

import io.github.cdimascio.dotenv.dotenv

object Config {
    val dotenv = dotenv()
    val appEnv = dotenv["APP_ENV"] ?: "production"

    val jwtSecret = dotenv["JWT_SECRET"] ?: "your-super-secret-jwt-secret"
    val dbUrl = dotenv["DB_URL"] ?: "jdbc:mysql://localhost:3306/travel_db"
    val dbUser = dotenv["DB_USER"] ?: "user"
    val dbPassword = dotenv["DB_PASSWORD"] ?: "password"

    val dbH2Url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;"
    val dbH2User = "sa"
    val dbH2Password = ""

    val redisHost = dotenv["REDIS_HOST"] ?: "localhost"
    val redisPort = dotenv["REDIS_PORT"]?.toInt() ?: 6379
    val s3Endpoint = dotenv["S3_ENDPOINT"] ?: "http://localhost:9000"
    val s3Bucket = dotenv["S3_BUCKET"] ?: "travel-images"
    val s3AccessKey = dotenv["S3_ACCESS_KEY"] ?: "minioadmin"
    val s3SecretKey = dotenv["S3_SECRET_KEY"] ?: "minioadmin"
}
