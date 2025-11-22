package com.travel.data.impl

import com.travel.data.table.Tours
import com.travel.domain.model.Tour
import com.travel.domain.repository.TourRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TourRepositoryImpl : TourRepository {
    override suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<Tour>, Long> =
        newSuspendedTransaction {
            val total = Tours.selectAll().count()
            val items =
                Tours.selectAll()
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toTour() }
            Pair(items, total)
        }

    override suspend fun findById(id: Long): Tour? =
        newSuspendedTransaction {
            Tours.selectAll().where { Tours.id eq id }.map { it.toTour() }.singleOrNull()
        }

    override suspend fun getPopular(): List<Tour> =
        newSuspendedTransaction {
            Tours.selectAll().limit(5).map { it.toTour() } // Just an example
        }
}

private fun ResultRow.toTour(): Tour =
    Tour(
        id = this[Tours.id],
        titleVi = this[Tours.titleVi] ?: "",
        titleEn = this[Tours.titleEn] ?: "",
        descriptionVi = this[Tours.descriptionVi] ?: "",
        descriptionEn = this[Tours.descriptionEn] ?: "",
        price = this[Tours.price]?.toDouble() ?: 0.0,
        durationHours = this[Tours.durationHours] ?: 0,
        destinationId = this[Tours.destinationId] ?: 0,
        images =
            this[Tours.images]?.let { kotlinx.serialization.json.Json.decodeFromString<List<String>>(it) }
                ?: emptyList(),
    )
