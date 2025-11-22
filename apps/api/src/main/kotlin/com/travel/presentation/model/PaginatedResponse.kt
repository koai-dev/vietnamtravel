package com.travel.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val data: List<T>,
    val pagination: Pagination
)

@Serializable
data class Pagination(
    val page: Int,
    val pageSize: Int,
    val total: Long,
    val totalPages: Int
)
