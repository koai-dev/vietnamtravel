package com.example.presentation

import com.example.application.*
import com.example.data.UserRole
import com.example.domain.AuthService
import com.example.domain.User
import com.example.domain.UserService
import io.ktor.server.auth.jwt.*

class AuthController(
    private val authService: AuthService
) {
    suspend fun register(request: RegisterRequest) {
        val user = User(
            email = request.email,
            passwordHash = request.password,
            name = request.name,
            avatarUrl = null,
            phone = null,
            role = UserRole.user
        )
        authService.register(user)
    }

    suspend fun login(request: LoginRequest): TokenResponse? {
        val tokenPair = authService.login(request.email, request.password)
        return tokenPair?.let { TokenResponse(it.accessToken, it.refreshToken) }
    }

    suspend fun refreshToken(request: RefreshTokenRequest): TokenResponse? {
        val tokenPair = authService.refreshToken(request.refreshToken)
        return tokenPair?.let { TokenResponse(it.accessToken, it.refreshToken) }
    }

    suspend fun logout(request: RefreshTokenRequest) {
        authService.logout(request.refreshToken)
    }
}

class UserController(
    private val userService: UserService
) {
    suspend fun getUsers(query: String?, page: Int, pageSize: Int): List<UserResponse> {
        return userService.getUsers(query, page, pageSize).map { it.toUserResponse() }
    }
    suspend fun getMe(principal: JWTPrincipal): UserResponse? {
        val userId = principal.payload.getClaim("userId").asLong()
        return userService.getUser(userId)?.toUserResponse()
    }
    suspend fun createUser(request: CreateUserRequest): UserResponse {
        val user = User(
            email = request.email,
            passwordHash = "password_placeholder", // Will be properly hashed in the service
            name = request.name,
            avatarUrl = null,
            phone = request.phone,
            role = request.role
        )
        return userService.createUser(user).toUserResponse()
    }
    suspend fun updateMe(principal: JWTPrincipal, request: UpdateUserRequest): UserResponse? {
        val userId = principal.payload.getClaim("userId").asLong()
        return userService.updateUser(userId, request.name, request.avatarUrl, request.phone)?.toUserResponse()
    }
    suspend fun deleteUser(id: Long) {
        userService.deleteUser(id)
    }
}

class DestinationController(private val destinationService: com.example.domain.DestinationService) {
    suspend fun getAll(lang: String): List<DestinationResponse> {
        return destinationService.getAll(lang).map { it.toDestinationResponse(lang) }
    }

    suspend fun getById(id: Long, lang: String): DestinationResponse? {
        return destinationService.getById(id)?.toDestinationResponse(lang)
    }
}

class TourController(private val tourService: com.example.domain.TourService) {
    suspend fun getAll(lang: String): List<TourResponse> {
        return tourService.getAll().map { it.toTourResponse(lang) }
    }

    suspend fun getById(id: Long, lang: String): TourResponse? {
        return tourService.getById(id)?.toTourResponse(lang)
    }

    suspend fun getPopular(lang: String): List<TourResponse> {
        return tourService.getPopular(lang).map { it.toTourResponse(lang) }
    }
}

class HotelController(private val hotelService: com.example.domain.HotelService) {
    suspend fun getAll(city: String?, sort: String?, page: Int, lang: String): List<HotelResponse> {
        return hotelService.getAll(city, sort, page, lang).map { it.toHotelResponse(lang) }
    }

    suspend fun getById(id: Long, lang: String): HotelResponse? {
        return hotelService.getById(id)?.toHotelResponse(lang)
    }
}

class BookingController(private val bookingService: com.example.domain.BookingService) {
    suspend fun createBooking(userId: Long, request: com.example.application.CreateBookingRequest): com.example.application.BookingResponse {
        val booking = com.example.domain.Booking(
            userId = userId,
            hotelId = request.hotelId,
            roomId = request.roomId,
            checkIn = java.time.LocalDate.parse(request.checkIn),
            checkOut = java.time.LocalDate.parse(request.checkOut),
            totalPrice = request.totalPrice,
            status = "pending"
        )
        return bookingService.createBooking(booking).toBookingResponse()
    }
}

class TrackingController(private val trackingService: com.example.domain.TrackingService) {
    suspend fun getStats(): com.example.application.TrackingStatsResponse {
        return trackingService.getStats()
    }
}
