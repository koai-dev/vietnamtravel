package com.travel.data.impl

import com.travel.data.model.ContactInfoResponse
import com.travel.data.model.HotelResponse
import com.travel.data.table.Hotels
import com.travel.data.table.Users
import com.travel.domain.repository.HotelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class HotelRepositoryImpl : HotelRepository {
    private fun toHotelResponse(row: ResultRow) =
        HotelResponse(
            id = row[Hotels.id],
            name = row[Hotels.nameEn], // Default to English, service will localize
            nameVi = row[Hotels.nameVi],
            nameEn = row[Hotels.nameEn],
            slug = row[Hotels.slug],
            description = row[Hotels.descriptionEn] ?: "", // Default to English, service will localize
            descriptionVi = row[Hotels.descriptionVi] ?: "",
            descriptionEn = row[Hotels.descriptionEn] ?: "",
            address = row[Hotels.address],
            city = row[Hotels.city],
            latitude = row[Hotels.latitude],
            longitude = row[Hotels.longitude],
            addressLink = row[Hotels.addressLink],
            contact = row[Hotels.contact]?.let { Json.decodeFromString<ContactInfoResponse>(it) },
            images = row[Hotels.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
            minPrice = row[Hotels.minPrice]?.toDouble(),
            maxPrice = row[Hotels.maxPrice]?.toDouble(),
            rating = row[Hotels.rating],
            reviewCount = row[Hotels.reviewCount],
            viewsCount = row[Hotels.viewsCount],
            favoritesCount = row[Hotels.favoritesCount],
            amenities = row[Hotels.amenities]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
            checkInTime = row[Hotels.checkInTime],
            checkOutTime = row[Hotels.checkOutTime],
            cancellationPolicy = row[Hotels.cancellationPolicy],
            childPolicy = row[Hotels.childPolicy],
            petPolicy = row[Hotels.petPolicy],
            tags = row[Hotels.tags]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
            externalBookingLinks = row[Hotels.externalBookingLinks]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
            hostId = row[Hotels.hostId],
            hostName = row.getOrNull(Users.name),
            hostAvatar = row.getOrNull(Users.avatarUrl),
        )

    private val selectQuery = Hotels.leftJoin(Users, { hostId }, { Users.id }).selectAll()

    override suspend fun createHotel(
        nameVi: String,
        nameEn: String,
        slug: String,
        descriptionVi: String?,
        descriptionEn: String?,
        address: String?,
        city: String?,
        latitude: Double?,
        longitude: Double?,
        addressLink: String?,
        contact: String?,
        images: String?,
        minPrice: Double?,
        maxPrice: Double?,
        amenities: String?,
        checkInTime: String?,
        checkOutTime: String?,
        cancellationPolicy: String?,
        childPolicy: String?,
        petPolicy: String?,
        tags: String?,
        externalBookingLinks: String?,
        hostId: Long?,
    ): HotelResponse =
        newSuspendedTransaction(Dispatchers.IO) {
            val id =
                Hotels.insert {
                    it[this.nameVi] = nameVi
                    it[this.nameEn] = nameEn
                    it[this.slug] = slug
                    it[this.descriptionVi] = descriptionVi
                    it[this.descriptionEn] = descriptionEn
                    it[this.address] = address
                    it[this.city] = city
                    it[this.latitude] = latitude
                    it[this.longitude] = longitude
                    it[this.addressLink] = addressLink
                    it[this.contact] = contact
                    it[this.images] = images
                    it[this.minPrice] = minPrice?.toBigDecimal()
                    it[this.maxPrice] = maxPrice?.toBigDecimal()
                    it[this.amenities] = amenities
                    it[this.checkInTime] = checkInTime
                    it[this.checkOutTime] = checkOutTime
                    it[this.cancellationPolicy] = cancellationPolicy
                    it[this.childPolicy] = childPolicy
                    it[this.petPolicy] = petPolicy
                    it[this.tags] = tags
                    it[this.externalBookingLinks] = externalBookingLinks
                    it[this.hostId] = hostId
                    it[this.createdAt] = CurrentDateTime
                    it[this.updatedAt] = CurrentDateTime
                } get Users.id
            getHotelById(id)!!
        }

    override suspend fun getAllHotels(): List<HotelResponse> =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.map(::toHotelResponse)
        }

    override suspend fun getHotelById(id: Long): HotelResponse? =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.where { Hotels.id eq id }.singleOrNull()?.let(::toHotelResponse)
        }

    override suspend fun getHotelBySlug(slug: String): HotelResponse? =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.where { Hotels.slug eq slug }.singleOrNull()?.let(::toHotelResponse)
        }

    override suspend fun getHotelsByHostId(hostId: Long): List<HotelResponse> =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.where { Hotels.hostId eq hostId }.map(::toHotelResponse)
        }

    override suspend fun getHotelsByCity(city: String): List<HotelResponse> =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.where { Hotels.city eq city }.map(::toHotelResponse)
        }

    override suspend fun getHotelsByTags(tags: List<String>): List<HotelResponse> =
        newSuspendedTransaction(Dispatchers.IO) {
            selectQuery.adjustWhere {
                val conditions = tags.map { Op.build { Hotels.tags like "%\"$it\"%" } }
                conditions.reduce { acc, op -> acc and op }
            }.map(::toHotelResponse)
        }

    override suspend fun updateHotel(
        id: Long,
        nameVi: String,
        nameEn: String,
        slug: String,
        descriptionVi: String?,
        descriptionEn: String?,
        address: String?,
        city: String?,
        latitude: Double?,
        longitude: Double?,
        addressLink: String?,
        contact: String?,
        images: String?,
        minPrice: Double?,
        maxPrice: Double?,
        amenities: String?,
        checkInTime: String?,
        checkOutTime: String?,
        cancellationPolicy: String?,
        childPolicy: String?,
        petPolicy: String?,
        tags: String?,
        externalBookingLinks: String?,
        hostId: Long?,
    ): HotelResponse? =
        newSuspendedTransaction(Dispatchers.IO) {
            val updatedRows =
                Hotels.update({ Hotels.id eq id }) {
                    it[this.nameVi] = nameVi
                    it[this.nameEn] = nameEn
                    it[this.slug] = slug
                    it[this.descriptionVi] = descriptionVi
                    it[this.descriptionEn] = descriptionEn
                    it[this.address] = address
                    it[this.city] = city
                    it[this.latitude] = latitude
                    it[this.longitude] = longitude
                    it[this.addressLink] = addressLink
                    it[this.contact] = contact
                    it[this.images] = images
                    it[this.minPrice] = minPrice?.toBigDecimal()
                    it[this.maxPrice] = maxPrice?.toBigDecimal()
                    it[this.amenities] = amenities
                    it[this.checkInTime] = checkInTime
                    it[this.checkOutTime] = checkOutTime
                    it[this.cancellationPolicy] = cancellationPolicy
                    it[this.childPolicy] = childPolicy
                    it[this.petPolicy] = petPolicy
                    it[this.tags] = tags
                    it[this.externalBookingLinks] = externalBookingLinks
                    it[this.hostId] = hostId
                    it[this.updatedAt] = CurrentDateTime
                }
            if (updatedRows > 0) getHotelById(id) else null
        }

    override suspend fun deleteHotel(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Hotels.deleteWhere { Hotels.id eq id } > 0
        }

    override suspend fun updateRating(
        id: Long,
        rating: Float,
        reviewCount: Int,
    ): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Hotels.update({ Hotels.id eq id }) {
                it[this.rating] = rating
                it[this.reviewCount] = reviewCount
                it[this.updatedAt] = CurrentDateTime
            } > 0
        }

    override suspend fun incrementViewsCount(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Hotels.update({ Hotels.id eq id }) {
                with(SqlExpressionBuilder) {
                    it.update(viewsCount, viewsCount + 1)
                }
            } > 0
        }

    override suspend fun incrementFavoritesCount(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Hotels.update({ Hotels.id eq id }) {
                with(SqlExpressionBuilder) {
                    it.update(favoritesCount, favoritesCount + 1)
                }
            } > 0
        }

    override suspend fun decrementFavoritesCount(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Hotels.update({ Hotels.id eq id }) {
                with(SqlExpressionBuilder) {
                    it.update(favoritesCount, favoritesCount - 1)
                }
            } > 0
        }

    override suspend fun isSlugExist(
        slug: String,
        id: Long?,
    ): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            val query = Hotels.selectAll().where { Hotels.slug eq slug }
            id?.let { query.andWhere { Hotels.id neq it } }
            query.count() > 0
        }

    override suspend fun count(): Long  = newSuspendedTransaction(Dispatchers.IO) {
        Hotels.selectAll().count()
    }
}
