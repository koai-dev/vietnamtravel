package com.example.data

import com.example.domain.AuthRepository
import com.example.domain.User
import com.example.domain.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class AuthRepositoryImpl : AuthRepository {
    override suspend fun findUserByEmail(email: String): User? = newSuspendedTransaction {
        Users.select { Users.email eq email }
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun saveUser(user: User): User = newSuspendedTransaction {
        val id = Users.insert {
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

class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(query: String?, page: Int, pageSize: Int): List<User> = newSuspendedTransaction {
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
    override suspend fun findById(id: Long): User? = newSuspendedTransaction {
        Users.select { Users.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }
    override suspend fun createUser(user: User): User {
        val id = newSuspendedTransaction {
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
    override suspend fun updateUser(id: Long, name: String?, avatarUrl: String?, phone: String?): User? = newSuspendedTransaction {
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

    override suspend fun getNewUsers(limit: Int, offset: Int): List<User> = newSuspendedTransaction {
        Users.selectAll()
            .orderBy(Users.createdAt, SortOrder.DESC)
            .limit(limit, offset.toLong())
            .map { it.toUser() }
    }

    override suspend fun countNewUsers(): Int = newSuspendedTransaction {
        Users.selectAll().count().toInt()
    }
}

private fun ResultRow.toUser(): User = User(
    id = this[Users.id],
    email = this[Users.email],
    passwordHash = this[Users.passwordHash],
    name = this[Users.name],
    avatarUrl = this[Users.avatarUrl],
    phone = this[Users.phone],
    role = this[Users.role]
)

class DestinationRepositoryImpl : com.example.domain.DestinationRepository {
    override suspend fun getAll(): List<com.example.domain.Destination> = newSuspendedTransaction {
        Destinations.selectAll().map { it.toDestination() }
    }

    override suspend fun findById(id: Long): com.example.domain.Destination? = newSuspendedTransaction {
        Destinations.select { Destinations.id eq id }.map { it.toDestination() }.singleOrNull()
    }
}

class TourRepositoryImpl : com.example.domain.TourRepository {
    override suspend fun getAll(): List<com.example.domain.Tour> = newSuspendedTransaction {
        Tours.selectAll().map { it.toTour() }
    }

    override suspend fun findById(id: Long): com.example.domain.Tour? = newSuspendedTransaction {
        Tours.select { Tours.id eq id }.map { it.toTour() }.singleOrNull()
    }

    override suspend fun getPopular(): List<com.example.domain.Tour> = newSuspendedTransaction {
        Tours.selectAll().limit(5).map { it.toTour() } // Just an example
    }
}

class HotelRepositoryImpl : com.example.domain.HotelRepository {
    override suspend fun getAll(city: String?, sort: String?, page: Int): List<com.example.domain.Hotel> = newSuspendedTransaction {
        Hotels.selectAll().map { it.toHotel() }
    }

    override suspend fun findById(id: Long): com.example.domain.Hotel? = newSuspendedTransaction {
        Hotels.select { Hotels.id eq id }.map { it.toHotel() }.singleOrNull()
    }
}

private fun ResultRow.toDestination(): com.example.domain.Destination = com.example.domain.Destination(
    id = this[Destinations.id],
    nameVi = this[Destinations.nameVi] ?: "",
    nameEn = this[Destinations.nameEn] ?: "",
    descriptionVi = this[Destinations.descriptionVi] ?: "",
    descriptionEn = this[Destinations.descriptionEn] ?: "",
    latitude = this[Destinations.latitude],
    longitude = this[Destinations.longitude],
    type = this[Destinations.type],
    images = this[Destinations.images]?.let { kotlinx.serialization.json.Json.decodeFromString<List<String>>(it) } ?: emptyList()
)

private fun ResultRow.toTour(): com.example.domain.Tour = com.example.domain.Tour(
    id = this[Tours.id],
    titleVi = this[Tours.titleVi] ?: "",
    titleEn = this[Tours.titleEn] ?: "",
    descriptionVi = this[Tours.descriptionVi] ?: "",
    descriptionEn = this[Tours.descriptionEn] ?: "",
    price = this[Tours.price]?.toDouble() ?: 0.0,
    durationHours = this[Tours.durationHours] ?: 0,
    destinationId = this[Tours.destinationId] ?: 0,
    images = this[Tours.images]?.let { kotlinx.serialization.json.Json.decodeFromString<List<String>>(it) } ?: emptyList()
)

private fun ResultRow.toHotel(): com.example.domain.Hotel = com.example.domain.Hotel(
    id = this[Hotels.id],
    nameVi = this[Hotels.nameVi],
    nameEn = this[Hotels.nameEn],
    descriptionVi = this[Hotels.descriptionVi] ?: "",
    descriptionEn = this[Hotels.descriptionEn] ?: "",
    address = this[Hotels.address],
    city = this[Hotels.city],
    latitude = this[Hotels.latitude],
    longitude = this[Hotels.longitude],
    hostId = this[Hotels.hostId],
    rating = this[Hotels.rating],
    reviewCount = this[Hotels.reviewCount]
)
