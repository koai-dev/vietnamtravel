package com.travel.core

import io.github.cdimascio.dotenv.dotenv

object Config {
    val env = dotenv()
    val appEnv = env["APP_ENV"] ?: "development"

    val jwtSecret = env["JWT_SECRET"] ?: "your-super-secret-jwt-secret"
    val dbUrl = env["DB_URL"] ?: "jdbc:mysql://localhost:3306/travel_db"
    val dbUser = env["DB_USER"] ?: "user"
    val dbPassword = env["DB_PASSWORD"] ?: "password"

    val dbH2Url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;"
    val dbH2User = "sa"
    val dbH2Password = ""

    val redisHost = env["REDIS_HOST"] ?: "localhost"
    val redisPort = env["REDIS_PORT"]?.toInt() ?: 6379
    val s3Endpoint = env["S3_ENDPOINT"] ?: "http://localhost:9000"
    val s3Bucket = env["S3_BUCKET"] ?: "travel-images"
    val s3AccessKey = env["S3_ACCESS_KEY"] ?: "minioadmin"
    val s3SecretKey = env["S3_SECRET_KEY"] ?: "minioadmin"
}
