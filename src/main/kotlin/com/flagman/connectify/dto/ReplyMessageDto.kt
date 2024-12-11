package com.flagman.connectify.dto

import com.flagman.connectify.models.Message
import com.flagman.connectify.models.User
import org.springframework.data.jpa.domain.AbstractPersistable_.id

data class ReplyMessageDto (
    val id: Long,
    val text: String,
    val timestamp: Long,
    val author: User,
)

fun toReplyMessageDto(msg: Message): ReplyMessageDto? {
    if (msg.replyTo == null)
        return null

    if (msg.replyTo!!.id == null
        || msg.replyTo!!.text == null
        || msg.replyTo!!.timestamp == null
        || msg.replyTo!!.author == null)
        return null

    return ReplyMessageDto(
        msg.replyTo!!.id!!,
        msg.replyTo!!.text!!,
        msg.replyTo!!.timestamp!!,
        msg.replyTo!!.author!!
    )
}