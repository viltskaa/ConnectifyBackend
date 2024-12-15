package com.flagman.connectify.dto

data class ChatUpdateDto(
    val chatId: Long,
    var name: String,
    var color: String,
    var icon: String,
)
