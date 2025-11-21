package com.travel.data.impl

import com.travel.data.model.DestinationRequest
import com.travel.data.table.DestinationStatus
import com.travel.data.table.DestinationType
import com.travel.data.table.Destinations
import com.travel.domain.model.Destination
import com.travel.domain.model.DestinationDetail
import com.travel.domain.repository.DestinationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class DestinationRepositoryImpl : DestinationRepository {
    override suspend fun count(): Long =
        newSuspendedTransaction(Dispatchers.IO) {
            Destinations.selectAll().count()
        }

    override suspend fun getAll(): List<Destination> =
        newSuspendedTransaction {
            Destinations.selectAll().map { it.toDestination() }
        }

    override suspend fun findById(id: Long): Destination? =
        newSuspendedTransaction {
            Destinations.selectAll().where { Destinations.id eq id }.map { it.toDestination() }.singleOrNull()
        }

    override suspend fun deleteDestination(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Destinations.deleteWhere { Destinations.id eq id } > 0
        }

    override suspend fun findByIdDetail(id: Long): DestinationDetail? =
        newSuspendedTransaction {
            Destinations.selectAll().where { Destinations.id eq id }.map { it.toDestinationDetail() }.singleOrNull()
        }

    override suspend fun findChildren(id: Long): List<Destination> =
        newSuspendedTransaction {
            Destinations.selectAll().where { Destinations.parentId eq id }.map { it.toDestination() }
        }

    override suspend fun findTree(id: Long): Destination? {
        return findById(id)?.let { buildTree(it) }
    }

    override suspend fun listRoot(): List<Destination> =
        newSuspendedTransaction {
            Destinations.selectAll().where { Destinations.parentId.isNull<Long?>() }.map { it.toDestination() }
        }

    override suspend fun create(request: DestinationRequest): Long =
        newSuspendedTransaction {
            Destinations.insert { row ->

                row[nameVi] = request.nameVi
                row[nameEn] = request.nameEn
                row[descriptionVi] = request.descriptionVi
                row[descriptionEn] = request.descriptionEn

                row[latitude] = request.latitude
                row[longitude] = request.longitude

                row[type] = DestinationType.valueOf(request.type)

                // JSON encode
                row[images] = Json.encodeToString(request.images)
                row[tags] = Json.encodeToString(request.tags)
                row[externalLinks] = Json.encodeToString(request.externalLinks)

                row[parentId] = request.parentId

                row[slug] = request.slug
                row[address] = request.address
                row[city] = request.city

                row[bestTimeToVisit] = request.bestTimeToVisit
                row[openingHours] = request.openingHours

                row[priceFrom] = request.priceFrom?.toBigDecimal()
                row[priceTo] = request.priceTo?.toBigDecimal()

                row[addressLink] = request.addressLink

                row[status] = DestinationStatus.valueOf(request.status)
                row[sortOrder] = request.sortOrder

                row[createdAt] = LocalDateTime.now()
                row[updatedAt] = LocalDateTime.now()
            } get Destinations.id
        }

    override suspend fun update(
        id: Long,
        request: DestinationRequest,
    ) = newSuspendedTransaction {
        Destinations.update({ Destinations.id eq id }) { row ->

            fun updateIfNotBlank(
                field: Column<String?>,
                value: String?,
            ) {
                if (!value.isNullOrBlank()) row[field] = value
            }

            fun updateIfNotNull(
                field: Column<Double?>,
                value: Double?,
            ) {
                if (value != null) row[field] = value
            }

            fun updateIfNotNullInt(
                field: Column<Int>,
                value: Int?,
            ) {
                if (value != null) row[field] = value
            }

            fun updateIfNotNullLong(
                field: Column<Long?>,
                value: Long?,
            ) {
                if (value != null) row[field] = value
            }

            fun updateIfListNotEmpty(
                field: Column<String?>,
                list: List<String>?,
            ) {
                if (!list.isNullOrEmpty()) {
                    row[field] = Json.encodeToString(list)
                }
            }

            // Strings
            updateIfNotBlank(Destinations.nameVi, request.nameVi)
            updateIfNotBlank(Destinations.nameEn, request.nameEn)
            updateIfNotBlank(Destinations.descriptionVi, request.descriptionVi)
            updateIfNotBlank(Destinations.descriptionEn, request.descriptionEn)
            updateIfNotBlank(Destinations.slug, request.slug)
            updateIfNotBlank(Destinations.address, request.address)
            updateIfNotBlank(Destinations.city, request.city)
            updateIfNotBlank(Destinations.bestTimeToVisit, request.bestTimeToVisit)
            updateIfNotBlank(Destinations.openingHours, request.openingHours)
            updateIfNotBlank(Destinations.addressLink, request.addressLink)

            // Numbers
            updateIfNotNull(Destinations.latitude, request.latitude)
            updateIfNotNull(Destinations.longitude, request.longitude)

            // Prices
            if (request.priceFrom != null) row[Destinations.priceFrom] = request.priceFrom.toBigDecimal()
            if (request.priceTo != null) row[Destinations.priceTo] = request.priceTo.toBigDecimal()

            // Enums
            if (request.type.isNotBlank()) {
                row[Destinations.type] = DestinationType.valueOf(request.type)
            }
            if (request.status.isNotBlank()) {
                row[Destinations.status] = DestinationStatus.valueOf(request.status)
            }

            // ParentId
            updateIfNotNullLong(Destinations.parentId, request.parentId)

            // JSON Lists
            updateIfListNotEmpty(Destinations.images, request.images)
            updateIfListNotEmpty(Destinations.tags, request.tags)
            updateIfListNotEmpty(Destinations.externalLinks, request.externalLinks)

            // Sort Order
            updateIfNotNullInt(Destinations.sortOrder, request.sortOrder)

            // Update timestamp
            row[Destinations.updatedAt] = LocalDateTime.now()
        }
    }

    private suspend fun buildTree(node: Destination): Destination {
        val children = findChildren(node.id).map { buildTree(it) }
        return node.copy(children = children)
    }

    override suspend fun search(
        query: String,
        types: List<DestinationType>,
    ): List<Destination> =
        newSuspendedTransaction {
            Destinations.selectAll()
                .where {
                    (Destinations.nameVi like "%$query%" or (Destinations.nameEn like "%$query%")) and
                        (Destinations.type inList types)
                }
                .limit(20)
                .map { it.toDestination() }
        }
}

private fun ResultRow.toDestination(): Destination =
    Destination(
        id = this[Destinations.id],
        nameVi = this[Destinations.nameVi] ?: "",
        nameEn = this[Destinations.nameEn] ?: "",
        descriptionVi = this[Destinations.descriptionVi] ?: "",
        descriptionEn = this[Destinations.descriptionEn] ?: "",
        latitude = this[Destinations.latitude],
        longitude = this[Destinations.longitude],
        type = this[Destinations.type],
        images = this[Destinations.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
        parentId = this[Destinations.parentId],
        city = this[Destinations.city],
        avgRating = this[Destinations.avgRating],
        reviewCount = this[Destinations.reviewCount],
        status = this[Destinations.status].name,
        viewsCount = this[Destinations.viewsCount],
    )

private fun ResultRow.toDestinationDetail(): DestinationDetail =
    DestinationDetail(
        id = this[Destinations.id],
        nameVi = this[Destinations.nameVi] ?: "",
        nameEn = this[Destinations.nameEn] ?: "",
        descriptionVi = this[Destinations.descriptionVi] ?: "",
        descriptionEn = this[Destinations.descriptionEn] ?: "",
        latitude = this[Destinations.latitude],
        longitude = this[Destinations.longitude],
        type = this[Destinations.type],
        images = this[Destinations.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
        parentId = this[Destinations.parentId],
        slug = this[Destinations.slug],
        address = this[Destinations.address],
        city = this[Destinations.city],
        tags = this[Destinations.tags]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
        bestTimeToVisit = this[Destinations.bestTimeToVisit],
        openingHours = this[Destinations.openingHours],
        priceFrom = this[Destinations.priceFrom],
        priceTo = this[Destinations.priceTo],
        externalLinks =
            this[Destinations.externalLinks]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
        addressLink = this[Destinations.addressLink],
        avgRating = this[Destinations.avgRating],
        reviewCount = this[Destinations.reviewCount],
        viewsCount = this[Destinations.viewsCount],
        favoritesCount = this[Destinations.favoritesCount],
        status = this[Destinations.status].name,
        sortOrder = this[Destinations.sortOrder],
    )
