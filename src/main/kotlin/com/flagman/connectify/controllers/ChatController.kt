package com.flagman.connectify.controllers

import com.flagman.connectify.dto.ChatCreateDto
import com.flagman.connectify.dto.ChatDto
import com.flagman.connectify.dto.MessageCreateDto
import com.flagman.connectify.dto.MessageDto
import com.flagman.connectify.dto.toChatDto
import com.flagman.connectify.models.Chat
import com.flagman.connectify.services.ChatService
import com.flagman.connectify.services.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val messageService: MessageService,
    private val chatService: ChatService,
    private val template: SimpMessagingTemplate
) {
    @MessageMapping("/sendMessage/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    fun handleChatMessage(messageCreateDto: MessageCreateDto): MessageDto? {
        var message = messageService.createMessage(messageCreateDto)
        if (message == null) {
            return null
        }
        return message
    }

    @MessageMapping("/createChat/{userId}")
    @SendTo("/topic/chats/{userId}")
    fun handleChatCreate(chatCreateDto: ChatCreateDto): ChatDto? {
        var chat = chatService.createChat(chatCreateDto)
        if (chat == null) {
            return null
        }

        return toChatDto(chat)
    }

    @SubscribeMapping("/chats/{userId}")
    fun sendChats(@DestinationVariable userId: Long): List<ChatDto> {
        val chats = chatService.getUserChats(userId).map { toChatDto(it) }
        return chats
    }

    @SubscribeMapping("/history/{chatId}")
    fun sendChatHistory(@DestinationVariable chatId: Long): List<MessageDto?> {
        return messageService.getAllMessages();
    }
}