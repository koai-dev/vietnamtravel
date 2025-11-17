package com.travel.data.mapper

import com.travel.data.model.UserResponse
import com.travel.domain.model.User

fun User.toUserResponse() =
    UserResponse(
        id = id,
        email = email,
        name = name,
        avatarUrl = avatarUrl,
        phone = phone,
        role = role,
    )
