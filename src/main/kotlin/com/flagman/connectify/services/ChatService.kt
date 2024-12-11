package com.flagman.connectify.services

import com.flagman.connectify.dto.ChatCreateDto
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
        chat.owner = user

        var newChat = chatRepository.save(chat)
        chatUsersService.addUserToChat(newChat, user)

        return newChat
    }
    fun registerMessageInChat(chatId: Long, message: Message): Boolean {
        val chat = chatRepository.findById(chatId).getOrNull()
        if (chat == null) return false

        chat.messages.add(message)
        chatRepository.save(chat)

        return true
    }
}