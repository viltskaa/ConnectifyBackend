package com.flagman.connectify.dto

import com.flagman.connectify.models.MessageType

data class MessageCreateDto (
    val text: String,
    val userId: Long,
    val chatId: Long,
    val replyId: Long? = null,
    val type: MessageType
)