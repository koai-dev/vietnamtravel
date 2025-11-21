package com.travel.seeder

import at.favre.lib.crypto.bcrypt.BCrypt
import com.travel.data.table.UserRole
import com.travel.domain.model.User
import com.travel.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DevSeeder : KoinComponent {
    private val authRepository: AuthRepository by inject()

    fun seedAdminUser() {
        runBlocking {
            if (authRepository.findUserByEmail("admin@admin.com") == null) {
                val hashedPassword = BCrypt.withDefaults().hashToString(12, "123456".toCharArray())
                val adminUser =
                    User(
                        email = "admin@admin.com",
                        passwordHash = hashedPassword,
                        name = "Admin User",
                        avatarUrl = null,
                        phone = null,
                        role = UserRole.admin,
                    )
                authRepository.saveUser(adminUser)
                println("Admin user created")
            }
        }
    }
}
