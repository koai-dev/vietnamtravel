package com.travel.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = text("password_hash")
    val name = varchar("name", 255).nullable()
    val avatarUrl = text("avatar_url").nullable()
    val phone = varchar("phone", 20).nullable()
    val role = enumerationByName("role", 10, UserRole::class)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

enum class UserRole {
    user,
    host,
    admin,
}
