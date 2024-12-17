package com.flagman.connectify.services

import com.flagman.connectify.dto.MessageCreateDto
import com.flagman.connectify.dto.MessageDto
import com.flagman.connectify.dto.MessageReportDto
import com.flagman.connectify.dto.toMessageDto
import com.flagman.connectify.dto.toMessageReportDto
import com.flagman.connectify.models.Message
import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.MessagesRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messagesRepository: MessagesRepository,
    private val userService: UserService,
    private val chatService: ChatService,
) {
    fun createMessage(messageCreateDto: MessageCreateDto): MessageDto? {
        var message = Message()

        message.text = messageCreateDto.text
        message.timestamp = System.currentTimeMillis()

        val user: User? = userService.getUserById(messageCreateDto.userId)
        if (user == null) {
            return null
        }
        message.author = user

        if (messageCreateDto.replyId != null) {
            val reply = getMessageById(messageCreateDto.replyId)
            if (reply != null) {
                message.replyTo = reply
            }
        }

        message = messagesRepository.save<Message>(message)
        chatService.registerMessageInChat(messageCreateDto.chatId, message)

        return toMessageDto(message)
    }


    fun getUserMessages(userId: Long): List<MessageDto?> {
        var chats = chatService.getUserChats(userId)
        return chats
            .flatMap { it -> it.messages }
            .map { it -> toMessageDto(it) }
    }

    fun getAllMessagesByChatId(chatId: Long): List<MessageReportDto> =
        messagesRepository.getAllByChatId(chatId).map { it -> toMessageReportDto(it) }.filterNotNull()

    fun getMessageById(id: Long): Message? = messagesRepository.findByIdOrNull(id)
}