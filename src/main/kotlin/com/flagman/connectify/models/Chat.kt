package com.flagman.connectify.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var chatName: String? = null
    var color: String? = null

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "chat", fetch = FetchType.EAGER)
    var users: Set<ChatUsers> = mutableSetOf()

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var owner: User? = null

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
    var messages: MutableList<Message> = mutableListOf()
}