package com.example.application

import com.example.core.pickLang
import com.example.domain.Destination
import com.example.domain.Hotel
import com.example.domain.Tour
import com.example.domain.User

fun User.toUserResponse() = UserResponse(
    id = id,
    email = email,
    name = name,
    avatarUrl = avatarUrl,
    phone = phone,
    role = role
)

fun Destination.toDestinationResponse(lang: String) = DestinationResponse(
    id = id,
    name = pickLang(lang, nameVi, nameEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    latitude = latitude,
    longitude = longitude,
    type = type?.name,
    images = images
)

fun Tour.toTourResponse(lang: String) = TourResponse(
    id = id,
    title = pickLang(lang, titleVi, titleEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    price = price,
    durationHours = durationHours,
    destinationId = destinationId,
    images = images
)

fun Hotel.toHotelResponse(lang: String) = HotelResponse(
    id = id,
    name = pickLang(lang, nameVi, nameEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    address = address,
    city = city,
    latitude = latitude,
    longitude = longitude,
    hostId = hostId,
    rating = rating,
    reviewCount = reviewCount
)

fun com.example.domain.Booking.toBookingResponse() = BookingResponse(
    id = id,
    userId = userId,
    hotelId = hotelId,
    roomId = roomId,
    checkIn = checkIn.toString(),
    checkOut = checkOut.toString(),
    totalPrice = totalPrice,
    status = status
)
