package com.flagman.connectify.controllers

import com.flagman.connectify.models.Message
import com.flagman.connectify.services.MessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class ChatController(
    private val messageService: MessageService,
) {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    fun handleChatMessage(message: Message): Message? {
        val timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
        val savedMessage = messageService.createMessage(message)
        return savedMessage
    }
}