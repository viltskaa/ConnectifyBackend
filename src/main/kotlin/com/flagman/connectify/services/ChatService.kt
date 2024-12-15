package com.flagman.connectify.services

import com.flagman.connectify.dto.ChatAppendDto
import com.flagman.connectify.dto.ChatCreateDto
import com.flagman.connectify.dto.ChatUpdateDto
import com.flagman.connectify.models.Chat
import com.flagman.connectify.models.Message
import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.ChatRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val userService: UserService,
    private val chatUsersService: ChatUsersService
) {
    fun getUserChats(userId: Long): List<Chat> = chatRepository.findAllByUserId(userId)
    fun createChat(chatDto: ChatCreateDto): Chat? {
        var chat = Chat()
        val user = userService.getUserById(chatDto.ownerId)
        if (user == null) {
            return null
        }

        chat.chatName = chatDto.name
        chat.color = chatDto.color
        chat.icon = chatDto.icon
        chat.owner = user

        var newChat = chatRepository.save(chat)
        chatUsersService.addUserToChat(newChat, user)

        for (userId in chatDto.users.split(",")) {
            val user = userService.getUserById(userId.toLong())
            if (user == null) continue

            chatUsersService.addUserToChat(newChat, user)
        }

        return chatRepository.findById(newChat.id!!).orElse(null)
    }

    fun appendChat(chatId: Long, chatAppendDto: ChatAppendDto): Chat? {
        val chat = chatRepository.findById(chatId).orElse(null)
        if (chat == null) {
            return null
        }

        for (userId in chatAppendDto.users.split(",")) {
            val user = userService.getUserById(userId.toLong())
            if (user == null) continue

            chatUsersService.addUserToChat(chat, user)
        }

        return chatRepository.findById(chatId).orElse(null)
    }

    fun updateChat(chatId: Long, chatUpdateDto: ChatUpdateDto): Chat? {
        val chat = chatRepository.findById(chatId).orElse(null)
        if (chat == null) {
            return null
        }

        chat.chatName = chatUpdateDto.name
        chat.color = chatUpdateDto.color
        chat.icon = chatUpdateDto.icon

        return chatRepository.save(chat)
    }

    fun registerMessageInChat(chatId: Long, message: Message): Boolean {
        val chat = chatRepository.findById(chatId).getOrNull()
        if (chat == null) return false

        message.chat = chat
        chat.messages.add(message)
        chatRepository.save(chat)

        return true
    }

    fun deleteChat(chatId: Long, userId: Long): Chat? {
        val chat = chatRepository.findById(chatId).getOrNull()
        if (chat == null) return null

        val user = userService.getUserById(userId)
        if (user == null) return null

        if (chat.owner!!.id!! != user.id!!) return null

        chatRepository.delete(chat)
        return chat
    }

    fun leaveChat(chatId: Long, userId: Long): Chat? {
        val chat = chatRepository.findById(chatId).getOrNull()
        if (chat == null) return null

        val user = userService.getUserById(userId)
        if (user == null) return null

        if (chat.owner!!.id!! == user.id!!) {
            if (chat.users.size > 1) {
                val newOwner = findNewOwner(chatId)
                chat.owner = newOwner
                chatRepository.save(chat)
            } else {
                deleteChat(chatId, userId)
                return chat
            }
        }

        chatUsersService.deleteUserFromChat(chat, user)
        return chatRepository.findById(chatId).getOrNull()
    }

    fun findNewOwner(chatId: Long) : User? {
        val chat = chatRepository.findById(chatId).getOrNull()
        if (chat == null) return null

        return chatUsersService.findNewOwner(chat)
    }
}