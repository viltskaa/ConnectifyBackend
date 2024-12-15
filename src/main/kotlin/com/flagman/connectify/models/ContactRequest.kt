package com.flagman.connectify.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class ContactRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var approved: Boolean? = false
    var canceled: Boolean? = false

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var fromUser: User? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var toUser: User? = null
}