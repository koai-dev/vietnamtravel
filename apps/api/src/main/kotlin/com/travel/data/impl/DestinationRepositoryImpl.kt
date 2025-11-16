package com.travel.data.impl

import com.travel.data.table.Destinations
import com.travel.domain.model.Destination
import com.travel.domain.repository.DestinationRepository
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class DestinationRepositoryImpl : DestinationRepository {
    override suspend fun getAll(): List<Destination> = newSuspendedTransaction {
        Destinations.selectAll().map { it.toDestination() }
    }

    override suspend fun findById(id: Long): Destination? = newSuspendedTransaction {
        Destinations.selectAll().where { Destinations.id eq id }.map { it.toDestination() }.singleOrNull()
    }
}

private fun ResultRow.toDestination(): Destination = Destination(
    id = this[Destinations.id],
    nameVi = this[Destinations.nameVi] ?: "",
    nameEn = this[Destinations.nameEn] ?: "",
    descriptionVi = this[Destinations.descriptionVi] ?: "",
    descriptionEn = this[Destinations.descriptionEn] ?: "",
    latitude = this[Destinations.latitude],
    longitude = this[Destinations.longitude],
    type = this[Destinations.type],
    images = this[Destinations.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
)