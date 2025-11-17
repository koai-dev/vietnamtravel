package com.travel.data.impl

import com.travel.data.table.Users
import com.travel.domain.model.User
import com.travel.domain.repository.AuthRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class AuthRepositoryImpl : AuthRepository {
    override suspend fun findUserByEmail(email: String): User? =
        newSuspendedTransaction {
            Users.selectAll().where { Users.email eq email }
                .map { it.toUser() }
                .singleOrNull()
        }

    override suspend fun saveUser(user: User): User =
        newSuspendedTransaction {
            val id =
                Users.insert {
                    it[email] = user.email
                    it[passwordHash] = user.passwordHash
                    it[name] = user.name
                    it[avatarUrl] = user.avatarUrl
                    it[phone] = user.phone
                    it[role] = user.role
                    it[createdAt] = LocalDateTime.now()
                    it[updatedAt] = LocalDateTime.now()
                } get Users.id
            user.copy(id = id)
        }
}

private fun ResultRow.toUser(): User =
    User(
        id = this[Users.id],
        email = this[Users.email],
        passwordHash = this[Users.passwordHash],
        name = this[Users.name],
        avatarUrl = this[Users.avatarUrl],
        phone = this[Users.phone],
        role = this[Users.role],
    )
