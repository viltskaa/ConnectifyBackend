package com.flagman.connectify.dto

import com.flagman.connectify.models.Chat

data class ChatDto(
    val id: Long,
    val chatName: String,
    val color: String,
    val icon: String?,
    val users: List<UserDto>,
    val owner: UserDto,
    val lastMessage: MessageDto?,
)

fun toChatDto(chat: Chat): ChatDto = ChatDto(
    chat.id!!,
    chat.chatName!!,
    chat.color!!,
    chat.icon,
    chat.users
        .map { it -> it.user }
        .filterNotNull()
        .map { it -> toUserDto(it, null) }
        .toList(),
    toUserDto(chat.owner!!, null),
    if (chat.messages.count() > 0) {
        toMessageDto(chat.messages.last())
    } else { null }
)
