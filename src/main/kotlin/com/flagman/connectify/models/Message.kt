package com.flagman.connectify.models

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
final class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var text: String? = null
    var timestamp: Long? = null

    @Enumerated(EnumType.STRING)
    var type: MessageType? = MessageType.COMMON

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_id", referencedColumnName = "id", nullable = true, unique = false)
    var replyTo: Message? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var author: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var chat: Chat? = null

    fun setTimestamp(timestamp: Long) { this.timestamp = timestamp }
}