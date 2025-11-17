package com.travel.domain.repository

import com.travel.domain.model.User

interface AuthRepository {
    suspend fun findUserByEmail(email: String): User?

    suspend fun saveUser(user: User): User
}
