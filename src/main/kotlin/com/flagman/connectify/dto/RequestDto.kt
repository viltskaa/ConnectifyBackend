package com.flagman.connectify.dto

import com.flagman.connectify.models.ContactRequest

data class RequestDto(
    val id: Long,
    val fromUser: UserDto,
    val toUser: UserDto,
    val approved: Boolean,
    val cancelled: Boolean,
)

fun toRequestDto(contactRequest: ContactRequest) = RequestDto(
    contactRequest.id!!,
    toUserDto(contactRequest.fromUser!!, null),
    toUserDto(contactRequest.toUser!!, null),
    contactRequest.approved!!,
    contactRequest.canceled!!
)
