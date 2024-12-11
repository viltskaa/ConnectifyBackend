package com.flagman.connectify.repositories

import com.flagman.connectify.models.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    @Query("select c from Chat c join ChatUsers cu on cu.chat.id = c.id where cu.user.id = :userId")
    fun findAllByUserId(@Param("userId") userId: Long): List<Chat>
}