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
final class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var user: User? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var contact: User? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    var request: ContactRequest? = null
}