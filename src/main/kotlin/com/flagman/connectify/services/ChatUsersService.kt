package com.flagman.connectify.services

import com.flagman.connectify.models.Chat
import com.flagman.connectify.models.ChatUsers
import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.ChatUsersRepository
import org.springframework.stereotype.Service

@Service
class ChatUsersService(
    private val chatUsersRepository: ChatUsersRepository
) {
    fun addUserToChat(chat: Chat, user: User): ChatUsers? {
        var obj = ChatUsers()
        obj.user = user
        obj.chat = chat

        return chatUsersRepository.save(obj)
    }
}