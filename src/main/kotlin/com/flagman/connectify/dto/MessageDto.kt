package com.flagman.connectify.dto

import com.flagman.connectify.models.Message
import com.flagman.connectify.models.MessageType

data class MessageDto (
    val id: Long,
    val text: String,
    val timestamp: Long,
    val author: UserDto,
    val chatId: Long,
    val replyTo: ReplyMessageDto?,
    val type: MessageType?
)

fun toMessageDto(msg: Message): MessageDto? {
    if (msg.id == null || msg.text == null || msg.timestamp == null || msg.author == null) {
        return null
    }

    val reply = toReplyMessageDto(msg);

    return MessageDto(
        msg.id!!,
        msg.text!!,
        msg.timestamp!!,
        toUserDto(msg.author!!, null),
        msg.chat!!.id!!,
        reply,
        msg.type
    )
}