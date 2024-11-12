package com.flagman.connectify.services

import com.flagman.connectify.models.Message
import com.flagman.connectify.repositories.MessagesRepository
import org.springframework.stereotype.Service

@Service
class MessageService(private val messagesRepository: MessagesRepository) {
    fun createMessage(message: Message): Message? {
        return messagesRepository.save<Message>(message)
    }
}