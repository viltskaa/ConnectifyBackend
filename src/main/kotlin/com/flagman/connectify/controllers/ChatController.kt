package com.flagman.connectify.controllers

import com.flagman.connectify.dto.ChatAppendDto
import com.flagman.connectify.dto.ChatCreateDto
import com.flagman.connectify.dto.ChatDto
import com.flagman.connectify.dto.ChatUpdateDto
import com.flagman.connectify.dto.MessageCreateDto
import com.flagman.connectify.dto.MessageDto
import com.flagman.connectify.dto.toChatDto
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
    private val messagingTemplate: SimpMessagingTemplate
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
    fun handleChatCreate(chatCreateDto: ChatCreateDto): ChatDto? {
        var chat = chatService.createChat(chatCreateDto)
        if (chat == null) {
            return null
        }

        for (user in chat.users) {
            messagingTemplate.convertAndSend("/topic/chats/${user.user!!.id!!}", toChatDto(chat))
        }

        return toChatDto(chat)
    }

    @MessageMapping("/deleteChat/{chatId}")
    fun handleDeleteChat(@DestinationVariable chatId: Long, user: UserIdDto) {
        var chat = chatService.deleteChat(chatId, user.userId)
        if (chat == null) {
            return
        }

        for (user in chat.users) {
            messagingTemplate.convertAndSend("/topic/deleteChat/${user.user!!.id!!}", chat.id!!)
        }
    }

    @MessageMapping("/leaveChat/{chatId}")
    fun handleLeaveChat(@DestinationVariable chatId: Long, user: UserIdDto) {
        var chat = chatService.leaveChat(chatId, user.userId)

        if (chat == null) {
            return
        }

        for (user in chat.users) {
            messagingTemplate.convertAndSend(
                "/topic/updateChat/${user.user!!.id!!}",
                toChatDto(chat)
            )
        }

        messagingTemplate.convertAndSend(
            "/topic/leaveChat/${user.userId}",
            toChatDto(chat)
        )
    }

    @MessageMapping("/appendChat/{chatId}")
    fun handleAppendChat(@DestinationVariable chatId: Long, chatAppendDto: ChatAppendDto) {
        val chat = chatService.appendChat(chatId, chatAppendDto)
        if (chat == null) {
            return
        }

        for (user in chat.users) {
            messagingTemplate.convertAndSend(
                "/topic/updateChat/${user.user!!.id!!}",
                toChatDto(chat)
            )
        }
    }

    @MessageMapping("/updateChat/{chatId}")
    fun handleUpdateChat(@DestinationVariable chatId: Long, chatUpdateDto: ChatUpdateDto) {
        val chat = chatService.updateChat(chatId, chatUpdateDto)
        if (chat == null) {
            return
        }

        for (user in chat.users) {
            messagingTemplate.convertAndSend(
                "/topic/updateChat/${user.user!!.id!!}",
                toChatDto(chat)
            )
        }
    }

    @SubscribeMapping("/chats/{userId}")
    fun sendChats(@DestinationVariable userId: Long): List<ChatDto> {
        val chats = chatService.getUserChats(userId).map { toChatDto(it) }
        return chats
    }

    @SubscribeMapping("/history/{userId}")
    fun sendChatHistory(@DestinationVariable userId: Long): List<MessageDto?> {
        return messageService.getUserMessages(userId)
    }
}

data class UserIdDto (val userId: Long)