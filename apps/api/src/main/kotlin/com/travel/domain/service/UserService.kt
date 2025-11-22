package com.travel.domain.service

import com.travel.data.mapper.toUserResponse
import com.travel.data.model.NewUsersResponse
import com.travel.domain.model.User
import com.travel.domain.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    suspend fun getUsers(
        query: String?,
        page: Int,
        pageSize: Int,
    ): Pair<List<User>, Long> = userRepository.getUsers(query, page, pageSize)

    suspend fun getUser(id: Long): User? = userRepository.findById(id)

    suspend fun createUser(user: User): User = userRepository.createUser(user)

    suspend fun updateUser(
        id: Long,
        name: String?,
        avatarUrl: String?,
        phone: String?,
    ): User? {
        return userRepository.updateUser(id, name, avatarUrl, phone)
    }

    suspend fun deleteUser(id: Long) = userRepository.deleteUser(id)

    suspend fun getNewUsers(
        limit: Int,
        offset: Int,
    ): NewUsersResponse {
        val users = userRepository.getNewUsers(limit, offset)
        val total = userRepository.countNewUsers()
        return NewUsersResponse(
            total = total,
            limit = limit,
            offset = offset,
            items = users.map { it.toUserResponse() },
        )
    }
}
