package com.example.domain

import com.example.data.DestinationType
import com.example.data.UserRole

interface AuthRepository {
    suspend fun findUserByEmail(email: String): User?
    suspend fun saveUser(user: User): User
}

interface UserRepository {
    suspend fun findById(id: Long): User?
    suspend fun updateUser(id: Long, name: String?, avatarUrl: String?, phone: String?): User?
}

interface DestinationRepository {
    suspend fun getAll(): List<Destination>
    suspend fun findById(id: Long): Destination?
}

interface TourRepository {
    suspend fun getAll(): List<Tour>
    suspend fun findById(id: Long): Tour?
    suspend fun getPopular(): List<Tour>
}

interface HotelRepository {
    suspend fun getAll(city: String?, sort: String?, page: Int): List<Hotel>
    suspend fun findById(id: Long): Hotel?
}

data class User(
    val id: Long = 0,
    val email: String,
    val passwordHash: String,
    val name: String?,
    val avatarUrl: String?,
    val phone: String?,
    val role: UserRole
)

data class Destination(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: DestinationType?,
    val images: List<String>
)

data class Tour(
    val id: Long = 0,
    val titleVi: String,
    val titleEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val price: Double,
    val durationHours: Int,
    val destinationId: Long,
    val images: List<String>
)

data class Hotel(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val address: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val hostId: Long?,
    val rating: Float,
    val reviewCount: Int
)
