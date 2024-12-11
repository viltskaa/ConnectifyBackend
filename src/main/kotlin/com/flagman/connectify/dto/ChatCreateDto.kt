package com.flagman.connectify.dto

data class ChatCreateDto(
    var name: String,
    var ownerId: Long,
    var color: String,
)
