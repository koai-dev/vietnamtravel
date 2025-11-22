package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactInfoResponse(
    val phone: String? = null,
    val email: String? = null,
    val website: String? = null,
    val facebook: String? = null,
    val zalo: String? = null,
    val whatsapp: String? = null,
)
