package com.example.domain

import com.example.application.UserTrackingDTO

interface UserTrackingRepository {
    suspend fun add(tracking: UserTrackingDTO)
}