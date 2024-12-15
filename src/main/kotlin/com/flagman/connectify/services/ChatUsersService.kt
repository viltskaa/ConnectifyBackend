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
        var chatUser = chatUsersRepository.findByChatIdAndUserId(chat.id!!, user.id!!)
        if (chatUser != null) {
            return chatUser
        }

        var obj = ChatUsers()
        obj.user = user
        obj.chat = chat
        obj.timestamp = System.currentTimeMillis()

        return chatUsersRepository.save(obj)
    }

    fun deleteUserFromChat(chat: Chat, user: User): ChatUsers? {
        var chatUser = chatUsersRepository.findByChatIdAndUserId(chat.id!!, user.id!!)
        if (chatUser == null) return null

        chatUsersRepository.delete(chatUser)
        return chatUser
    }

    fun findNewOwner(chat: Chat): User? {
        val newOwner = chatUsersRepository.findFirstByChatIdOrderByTimestampDesc(chat.id!!)
        if (newOwner == null) return null

        return newOwner.user
    }
}