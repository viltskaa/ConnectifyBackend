package com.flagman.connectify.dto

import com.flagman.connectify.models.Message
import java.sql.Date

data class MessageReportDto (
    val id: Long,
    val message: String,
    val time: String,
    val replyText: String?,
    val author: String,
)

fun toMessageReportDto(message: Message): MessageReportDto? {
    if (message.id == null
        || message.text == null
        || message.timestamp == null
        || message.author == null) {
        return null
    }

    return MessageReportDto (
        message.id!!,
        message.text!!,
        Date(message.timestamp!!).toString(),
        if (message.replyTo == null) "" else message.replyTo!!.text!!,
        message.author!!.username!!
    )
}