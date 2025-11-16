package com.travel.data.impl

import com.travel.data.table.Hotels
import com.travel.domain.model.Hotel
import com.travel.domain.repository.HotelRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class HotelRepositoryImpl : HotelRepository {
    override suspend fun getAll(city: String?, sort: String?, page: Int): List<Hotel> = newSuspendedTransaction {
        Hotels.selectAll().map { it.toHotel() }
    }

    override suspend fun findById(id: Long): Hotel? = newSuspendedTransaction {
        Hotels.select { Hotels.id eq id }.map { it.toHotel() }.singleOrNull()
    }
}

private fun ResultRow.toHotel(): Hotel = Hotel(
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