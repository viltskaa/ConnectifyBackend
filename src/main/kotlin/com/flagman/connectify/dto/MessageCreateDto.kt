package com.flagman.connectify.dto

data class MessageCreateDto (
    val text: String,
    val jwt: String,
    val chatId: Long,
    val replyId: Long? = null,
)