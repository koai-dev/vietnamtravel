package com.travel.domain.service

import com.travel.data.model.HotelRequest
import com.travel.data.model.HotelResponse
import com.travel.data.model.RatingUpdateRequest
import com.travel.domain.repository.HotelRepository
import com.travel.helper.toSlug
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HotelServiceImpl(
    private val hotelRepository: HotelRepository,
    private val imageMappingService: ImageMappingService,
) : HotelService {
    private fun HotelResponse.toLocale(lang: String): HotelResponse {
        return this.copy(
            name = if (lang == "vi") this.nameVi else this.nameEn,
            description = if (lang == "vi") this.descriptionVi else this.descriptionEn,
        )
    }

    override suspend fun createHotel(
        hotelRequest: HotelRequest,
        lang: String,
    ): HotelResponse {
        // Auto-generate slug if it's not provided
        if (hotelRequest.slug.isBlank()) {
            hotelRequest.slug = (if (lang == "vi") hotelRequest.nameVi else hotelRequest.nameEn).toSlug()
        }
        // Ensure slug is unique
        if (hotelRepository.isSlugExist(hotelRequest.slug)) {
            throw IllegalArgumentException("Slug '${hotelRequest.slug}' already exists.")
        }

        val resolvedImages =
            imageMappingService.resolveImages(
                hotelRequest.images?.let { Json.encodeToString(it) },
                hotelRequest.tempUrlMap ?: emptyMap(),
            )

        val hotel =
            hotelRepository.createHotel(
                nameVi = hotelRequest.nameVi,
                nameEn = hotelRequest.nameEn,
                slug = hotelRequest.slug,
                descriptionVi = hotelRequest.descriptionVi,
                descriptionEn = hotelRequest.descriptionEn,
                address = hotelRequest.address,
                city = hotelRequest.city,
                latitude = hotelRequest.latitude,
                longitude = hotelRequest.longitude,
                addressLink = hotelRequest.addressLink,
                contact = hotelRequest.contact?.let { Json.encodeToString(it) },
                images = resolvedImages,
                minPrice = hotelRequest.minPrice,
                maxPrice = hotelRequest.maxPrice,
                amenities = hotelRequest.amenities?.let { Json.encodeToString(it) },
                checkInTime = hotelRequest.checkInTime,
                checkOutTime = hotelRequest.checkOutTime,
                cancellationPolicy = hotelRequest.cancellationPolicy,
                childPolicy = hotelRequest.childPolicy,
                petPolicy = hotelRequest.petPolicy,
                tags = hotelRequest.tags?.let { Json.encodeToString(it) },
                externalBookingLinks = hotelRequest.externalBookingLinks?.let { Json.encodeToString(it) },
                hostId = hotelRequest.hostId,
            )
        return hotel.toLocale(lang)
    }

    override suspend fun getAllHotels(lang: String, page: Int, pageSize: Int): Pair<List<HotelResponse>, Long> {
        val (hotels, total) = hotelRepository.getAllHotels(page, pageSize)
        return Pair(hotels.map { it.toLocale(lang) }, total)
    }

    override suspend fun getHotelById(
        id: Long,
        lang: String,
    ): HotelResponse? {
        hotelRepository.incrementViewsCount(id)
        return hotelRepository.getHotelById(id)?.toLocale(lang)
    }

    override suspend fun getHotelBySlug(
        slug: String,
        lang: String,
    ): HotelResponse? {
        val hotel = hotelRepository.getHotelBySlug(slug)
        hotel?.let { hotelRepository.incrementViewsCount(it.id) }
        return hotel?.toLocale(lang)
    }

    override suspend fun updateHotel(
        id: Long,
        hotelRequest: HotelRequest,
        lang: String,
    ): HotelResponse? {
        if (hotelRequest.slug.isBlank()) {
            hotelRequest.slug = (if (lang == "vi") hotelRequest.nameVi else hotelRequest.nameEn).toSlug()
        }
        if (hotelRepository.isSlugExist(hotelRequest.slug, id)) {
            throw IllegalArgumentException("Slug '${hotelRequest.slug}' already exists.")
        }

        val resolvedImages =
            imageMappingService.resolveImages(
                hotelRequest.images?.let { Json.encodeToString(it) },
                hotelRequest.tempUrlMap ?: emptyMap(),
            )

        val updatedHotel =
            hotelRepository.updateHotel(
                id = id,
                nameVi = hotelRequest.nameVi,
                nameEn = hotelRequest.nameEn,
                slug = hotelRequest.slug,
                descriptionVi = hotelRequest.descriptionVi,
                descriptionEn = hotelRequest.descriptionEn,
                address = hotelRequest.address,
                city = hotelRequest.city,
                latitude = hotelRequest.latitude,
                longitude = hotelRequest.longitude,
                addressLink = hotelRequest.addressLink,
                contact = hotelRequest.contact?.let { Json.encodeToString(it) },
                images = resolvedImages,
                minPrice = hotelRequest.minPrice,
                maxPrice = hotelRequest.maxPrice,
                amenities = hotelRequest.amenities?.let { Json.encodeToString(it) },
                checkInTime = hotelRequest.checkInTime,
                checkOutTime = hotelRequest.checkOutTime,
                cancellationPolicy = hotelRequest.cancellationPolicy,
                childPolicy = hotelRequest.childPolicy,
                petPolicy = hotelRequest.petPolicy,
                tags = hotelRequest.tags?.let { Json.encodeToString(it) },
                externalBookingLinks = hotelRequest.externalBookingLinks?.let { Json.encodeToString(it) },
                hostId = hotelRequest.hostId,
            )
        return updatedHotel?.toLocale(lang)
    }

    override suspend fun deleteHotel(id: Long): Boolean {
        return hotelRepository.deleteHotel(id)
    }

    override suspend fun incrementViews(id: Long): Boolean {
        return hotelRepository.incrementViewsCount(id)
    }

    override suspend fun updateRating(
        id: Long,
        ratingUpdateRequest: RatingUpdateRequest,
    ): Boolean {
        val hotel = hotelRepository.getHotelById(id) ?: return false
        val currentRating = hotel.rating
        val currentReviewCount = hotel.reviewCount
        val newReviewCount = currentReviewCount + 1
        val newRating = ((currentRating * currentReviewCount) + ratingUpdateRequest.rating) / newReviewCount
        return hotelRepository.updateRating(id, newRating, newReviewCount)
    }
}
