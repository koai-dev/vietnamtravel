package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewUsersResponse(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val items: List<UserResponse>,
)

@Serializable
data class BookingDashboardResponse(
    val id: Long,
    val userId: Long,
    val hotelId: Long,
    val roomId: Long,
    val checkIn: String,
    val checkOut: String,
    val totalPrice: Double,
    val status: String,
    val hotelName: String?,
    val userName: String?,
)

@Serializable
data class DashboardStatsResponse(
    val totalDestinations: Long,
    val totalHotels: Long,
    val totalBookings: Long,
    val totalUsers: Long,
    val totalRevenue: Double,
    val pendingBookings: Long,
    val recentBookings: List<BookingDashboardResponse>,
    val recentReviews: List<ReviewResponse>,
)
