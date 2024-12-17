package com.flagman.connectify.repositories

import com.flagman.connectify.models.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessagesRepository: JpaRepository<Message, Long> {
    fun getAllByChatId(chatId: Long): List<Message>
}