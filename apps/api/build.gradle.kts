val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val koinVersion: String by project
val mysqlConnectorVersion: String by project
val flywayVersion: String by project
val bcryptVersion: String by project
val redisVersion: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    kotlin("plugin.serialization") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.travel.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-request-validation-jvm")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // Database
    implementation("com.h2database:h2:2.2.224")
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-mysql:$flywayVersion")

    // DI
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    // Security
    implementation("at.favre.lib:bcrypt:$bcryptVersion")

    // Redis
    implementation("redis.clients:jedis:$redisVersion")

    // Dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // S3
    implementation("aws.sdk.kotlin:s3:1.2.1")

    // Testing
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
ktlint {
    version.set("1.2.1") // hoặc bất kỳ bản mới nhất
    debug.set(false)
    verbose.set(false)
    android.set(false) // bạn đang làm Ktor server -> để false
    ignoreFailures.set(false)
}

tasks.named("check") {
    dependsOn("ktlintCheck")
}
