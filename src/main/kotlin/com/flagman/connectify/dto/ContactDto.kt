package com.flagman.connectify.dto

import com.flagman.connectify.models.Contacts

data class ContactDto (
    val id: Long,
    val contact: UserDto
)

fun toContactDto(contact: Contacts) = ContactDto(
    contact.id!!,
    toUserDto(contact.contact!!, null)
)