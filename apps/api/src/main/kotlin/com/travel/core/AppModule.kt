package com.travel.core

import com.travel.data.impl.*
import com.travel.domain.repository.*
import com.travel.domain.service.*
import com.travel.domain.service.UserTrackingRepository
import com.travel.presentation.controller.*
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
    single<LocalFoodRepository> { LocalFoodRepositoryImpl() }
    single<RestaurantRepository> { RestaurantRepositoryImpl() }

    single { AuthService(get(), get()) }
    single { UserService(get()) }
    single { DestinationService(get(), get(), get(), get()) }
    single { TourService(get(), get()) }
    single { HotelService(get(), get()) }
    single { BookingService(get(), get(), get()) }
    single<TrackingService> { TrackingServiceImpl(get()) }
    single { LocalFoodService(get(), get()) }
    single { RestaurantService(get(), get(), get()) }

    single { AuthController(get()) }
    single { UserController(get()) }
    single { DestinationController(get()) }
    single { TourController(get()) }
    single { HotelController(get()) }
    single { BookingController(get()) }
    single { TrackingController(get()) }
    single { LocalFoodController(get()) }
    single { RestaurantController(get()) }
}
