package com.flagman.connectify.models

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null
    private var text: String? = null
    private var timestamp: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    private var author: User? = null

    fun setTimestamp(timestamp: Long) { this.timestamp = timestamp }
}