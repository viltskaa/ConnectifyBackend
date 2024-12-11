package com.flagman.connectify.dto

import com.flagman.connectify.models.Chat
import com.flagman.connectify.models.User

data class ChatDto(
    val id: Long,
    val chatName: String,
    val color: String,
    val users: List<UserDto>,
    val owner: User,
    val lastMessage: String
)

fun toChatDto(chat: Chat): ChatDto = ChatDto(
    chat.id!!,
    chat.chatName!!,
    chat.color!!,
    chat.users
        .map { it -> it.user }
        .filterNotNull()
        .map { it -> toUserDto(it, null) }
        .toList(),
    chat.owner!!,
    ""
)
