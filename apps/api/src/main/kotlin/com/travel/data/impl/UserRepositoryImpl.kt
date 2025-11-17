package com.travel.data.impl

import com.travel.data.table.Users
import com.travel.domain.model.User
import com.travel.domain.repository.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(
        query: String?,
        page: Int,
        pageSize: Int,
    ): List<User> =
        newSuspendedTransaction {
            val queryBuilder = Users.selectAll()
            query?.let {
                queryBuilder.andWhere {
                    (Users.name like "%$it%") or
                        (Users.email like "%$it%") or
                        (Users.phone like "%$it%")
                }
            }
            queryBuilder.limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                .map { it.toUser() }
        }

    override suspend fun findById(id: Long): User? =
        newSuspendedTransaction {
            Users.selectAll().where { Users.id eq id }
                .map { it.toUser() }
                .singleOrNull()
        }

    override suspend fun createUser(user: User): User {
        val id =
            newSuspendedTransaction {
                Users.insert {
                    it[email] = user.email
                    it[passwordHash] = user.passwordHash
                    it[name] = user.name
                    it[avatarUrl] = user.avatarUrl
                    it[phone] = user.phone
                    it[role] = user.role
                } get Users.id
            }
        return findById(id)!!
    }

    override suspend fun updateUser(
        id: Long,
        name: String?,
        avatarUrl: String?,
        phone: String?,
    ): User? =
        newSuspendedTransaction {
            Users.update({ Users.id eq id }) {
                name?.let { uName -> it[Users.name] = uName }
                avatarUrl?.let { uAvatarUrl -> it[Users.avatarUrl] = uAvatarUrl }
                phone?.let { uPhone -> it[Users.phone] = uPhone }
                it[updatedAt] = LocalDateTime.now()
            }
            findById(id)
        }

    override suspend fun deleteUser(id: Long) {
        newSuspendedTransaction {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }

    override suspend fun getNewUsers(
        limit: Int,
        offset: Int,
    ): List<User> =
        newSuspendedTransaction {
            Users.selectAll()
                .orderBy(Users.createdAt, SortOrder.DESC)
                .limit(limit, offset.toLong())
                .map { it.toUser() }
        }

    override suspend fun countNewUsers(): Int =
        newSuspendedTransaction {
            Users.selectAll().count().toInt()
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
