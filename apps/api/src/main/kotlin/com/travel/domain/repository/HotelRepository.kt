package com.travel.domain.repository

import com.travel.data.model.HotelResponse

interface HotelRepository {
    suspend fun createHotel(
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
        contact: String?, // JSON
        images: String?, // JSON
        minPrice: Double?,
        maxPrice: Double?,
        amenities: String?, // JSON
        checkInTime: String?,
        checkOutTime: String?,
        cancellationPolicy: String?,
        childPolicy: String?,
        petPolicy: String?,
        tags: String?, // JSON
        externalBookingLinks: String?, // JSON
        hostId: Long?,
    ): HotelResponse

    suspend fun getAllHotels(): List<HotelResponse>

    suspend fun getHotelById(id: Long): HotelResponse?

    suspend fun getHotelBySlug(slug: String): HotelResponse?

    suspend fun getHotelsByHostId(hostId: Long): List<HotelResponse>

    suspend fun getHotelsByCity(city: String): List<HotelResponse>

    suspend fun getHotelsByTags(tags: List<String>): List<HotelResponse>

    suspend fun updateHotel(
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
    ): HotelResponse?

    suspend fun deleteHotel(id: Long): Boolean

    suspend fun updateRating(
        id: Long,
        rating: Float,
        reviewCount: Int,
    ): Boolean

    suspend fun incrementViewsCount(id: Long): Boolean

    suspend fun incrementFavoritesCount(id: Long): Boolean

    suspend fun decrementFavoritesCount(id: Long): Boolean

    suspend fun isSlugExist(
        slug: String,
        id: Long? = null,
    ): Boolean
}
