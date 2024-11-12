package com.flagman.connectify.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
final data class Role(var roleName: String = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}