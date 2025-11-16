package com.example.core

import com.example.data.AuthRepositoryImpl
import com.example.data.RedisRepositoryImpl
import com.example.data.UserRepositoryImpl
import com.example.domain.AuthRepository
import com.example.data.*
import com.example.domain.*
import com.example.presentation.*
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<RedisRepository> { RedisRepositoryImpl() }
    single<DestinationRepository> { DestinationRepositoryImpl() }
    single<TourRepository> { TourRepositoryImpl() }
    single<HotelRepository> { HotelRepositoryImpl() }
    single<BookingRepository> { BookingRepositoryImpl() }
    single<RoomRepository> { RoomRepositoryImpl() }
    single<UserTrackingRepository> { UserTrackingRepositoryImpl() }

    single { AuthService(get(), get()) }
    single { UserService(get()) }
    single { DestinationService(get(), get()) }
    single { TourService(get(), get()) }
    single { HotelService(get(), get()) }
    single { BookingService(get(), get(), get()) }
    single<TrackingService> { TrackingServiceImpl(get()) }

    single { AuthController(get()) }
    single { UserController(get()) }
    single { DestinationController(get()) }
    single { TourController(get()) }
    single { HotelController(get()) }
    single { BookingController(get()) }
    single { TrackingController(get()) }
}
