package com.travel.core

import com.travel.data.impl.*
import com.travel.domain.repository.*
import com.travel.domain.service.*
import com.travel.domain.service.HotelServiceImpl
import com.travel.domain.service.impl.NotificationServiceImpl
import com.travel.presentation.controller.*
import io.github.cdimascio.dotenv.dotenv
import org.koin.dsl.module

val appModule =
    module {
        // Configurations
        single { dotenv() }
        single { loadUploadConfig(get()) }

        // Repositories
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
        single<NotificationRepository> { NotificationRepositoryImpl() }

        // File Uploader
        single<FileUploader> {
            val config: UploadConfig = get()
            if (config.uploadProvider.equals("s3", ignoreCase = true)) {
                S3FileUploader(config)
            } else {
                LocalFileUploader(config)
            }
        }

        // Services
        single { AuthService(get(), get()) }
        single { UserService(get()) }
        single { DestinationService(get(), get(), get(), get(), get()) }
        single { TourService(get(), get()) }
        single<HotelService> { HotelServiceImpl(get(), get()) }
        single { BookingService(get(), get(), get()) }
        single<TrackingService> { TrackingServiceImpl(get()) }
        single { LocalFoodService(get(), get(), get()) }
        single { RestaurantService(get(), get(), get(), get()) }
        single<NotificationService> { NotificationServiceImpl(get(), get()) }
        single { UploadService(get()) }
        single { ImageMappingService() }

        // Controllers
        single { AuthController(get()) }
        single { UserController(get()) }
        single { DestinationController(get()) }
        single { TourController(get()) }
        single { HotelController(get()) }
        single { BookingController(get()) }
        single { TrackingController(get()) }
        single { LocalFoodController(get()) }
        single { RestaurantController(get()) }
        single { NotificationController(get()) }
        single { UploadController(get()) }
    }
