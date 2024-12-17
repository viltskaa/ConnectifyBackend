package com.flagman.connectify.services

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Service

@Service
class MistralAiService(
    private val chatModel: ChatModel
) {
    fun getResponse(message: String): String =
        chatModel.call("Выдели из текста главную мысль. $message")

    fun getDirectResponse(message: String): String =
         chatModel.call(message)
}