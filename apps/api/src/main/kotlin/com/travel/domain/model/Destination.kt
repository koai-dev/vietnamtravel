package com.travel.domain.model

import com.travel.data.table.DestinationType

data class Destination(
    val id: Long = 0,
    val nameVi: String,
    val nameEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val latitude: Double?,
    val longitude: Double?,
    val type: DestinationType?,
    val images: List<String>
)