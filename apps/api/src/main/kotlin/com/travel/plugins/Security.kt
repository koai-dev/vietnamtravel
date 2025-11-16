package com.travel.plugins

import com.travel.core.Config
import com.travel.core.TokenService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    authentication {
        jwt {
            realm = Config.env["JWT_REALM"] ?: "ktor sample app"
            verifier(TokenService.verifier)
            validate { credential ->
                if (credential.payload.getClaim("userId").asLong() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
