package com.travel.seeder

import com.travel.data.table.UserRole
import com.travel.domain.model.User
import com.travel.domain.service.AuthService
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DevSeeder : KoinComponent {

    private val authService: AuthService by inject()

    fun seedAdminUser() {
        runBlocking {
            if (authService.getUserByEmail("admin@admin.com") == null) {
                val adminUser = User(
                    email = "admin@admin.com",
                    passwordHash = "123456",
                    name = "Admin User",
                    avatarUrl = null,
                    phone = null,
                    role = UserRole.admin,
                )
                authService.register(adminUser)
                println("Admin user created")
            }
        }
    }
}
