package com.travel.domain.model

data class Room(
    val id: Long = 0,
    val hotelId: Long,
    val roomTypeVi: String,
    val roomTypeEn: String,
    val maxGuest: Int,
    val pricePerNight: Double,
    val totalRooms: Int,
    val availableRooms: Int,
    val amenities: List<String>
)