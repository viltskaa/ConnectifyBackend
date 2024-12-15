package com.flagman.connectify.dto

import com.flagman.connectify.models.User

data class UserDto(
    val id: Long,
    val username: String,
    val email: String,
    val jwt: String?,
    val online: Boolean?,
    val lastSeen: Long?,
)

fun toUserDto(user: User, jwt: String?) = UserDto(
    user.id!!,
    user.username!!,
    user.username!!,
    jwt,
    user.online,
    user.lastSeen
)
