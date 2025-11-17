package com.travel.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactInfoResponse(
    val phone: String?,
    val email: String?,
    val website: String?,
    val facebook: String?,
    val zalo: String?,
    val whatsapp: String?,
)