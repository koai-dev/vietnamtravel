package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomRequest(
    val hotelId: Long,
    val roomTypeVi: String?,
    val roomTypeEn: String?,
    val maxGuest: Int?,
    val pricePerNight: Double?,
    val totalRooms: Int?,
    val availableRooms: Int?,
    val amenities: List<String>?,
)