package com.travel.domain.repository

import com.travel.domain.model.User

interface UserRepository {
    suspend fun getUsers(
        query: String?,
        page: Int,
        pageSize: Int,
    ): List<User>

    suspend fun findById(id: Long): User?

    suspend fun createUser(user: User): User

    suspend fun updateUser(
        id: Long,
        name: String?,
        avatarUrl: String?,
        phone: String?,
    ): User?

    suspend fun deleteUser(id: Long)

    suspend fun getNewUsers(
        limit: Int,
        offset: Int,
    ): List<User>

    suspend fun countNewUsers(): Int
}
