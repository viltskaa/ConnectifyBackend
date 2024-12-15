package com.flagman.connectify.dto

data class RequestCreateDto(
    val fromUser: Long,
    val toUser: Long,
)