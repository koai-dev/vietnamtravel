package com.travel.presentation.controller

import com.travel.data.model.DashboardStatsResponse
import com.travel.domain.repository.*
import io.ktor.server.application.*

class DashboardController(
    private val destinationRepository: DestinationRepository,
    private val hotelRepository: HotelRepository,
    private val bookingRepository: BookingRepository,
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
) : BaseController() {
    suspend fun getDashboardStats(call: ApplicationCall) {
        val totalDestinations = destinationRepository.count()
        val totalHotels = hotelRepository.count()
        val totalBookings = bookingRepository.count()
        val totalUsers = userRepository.countNewUsers()
        val totalRevenue = bookingRepository.sumTotalPrice()
        val pendingBookings = bookingRepository.countByStatus("pending")
        val recentBookings = bookingRepository.getRecentBookings(5)
        val recentReviews = reviewRepository.getRecentReviews(5)

        respondWith(
            call,
            DashboardStatsResponse(
                totalDestinations = totalDestinations,
                totalHotels = totalHotels,
                totalBookings = totalBookings,
                totalUsers = totalUsers.toLong(),
                totalRevenue = totalRevenue,
                pendingBookings = pendingBookings,
                recentBookings = recentBookings,
                recentReviews = recentReviews,
            ),
        )
    }
}
