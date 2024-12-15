package com.flagman.connectify.repositories

import com.flagman.connectify.models.ChatUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatUsersRepository: JpaRepository<ChatUsers, Long> {
    fun findByChatIdAndUserId(chatId: Long, userId: Long): ChatUsers?
    fun findFirstByChatIdOrderByTimestampDesc(chatId: Long): ChatUsers?
}