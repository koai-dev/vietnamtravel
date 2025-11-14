package com.example.core

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.*

val LanguagePlugin = createApplicationPlugin(name = "LanguagePlugin") {
    onCall { call ->
        val lang = call.request.queryParameters["lang"]
            ?: call.request.headers["Accept-Language"]?.split(",")?.firstOrNull()?.split(";")?.firstOrNull()
            ?: "en"
        call.attributes.put(AttributeKey("lang"), lang)
    }
}

fun ApplicationCall.lang(): String = attributes.get(AttributeKey("lang")) ?: "en"

fun pickLang(lang: String, vi: String, en: String) = if (lang.startsWith("vi")) vi else en
fun <T> pickLang(lang: String, vi: T, en: T) = if (lang.startsWith("vi")) vi else en
