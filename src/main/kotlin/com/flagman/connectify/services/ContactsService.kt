package com.flagman.connectify.services

import com.flagman.connectify.models.ContactRequest
import com.flagman.connectify.models.Contacts
import com.flagman.connectify.repositories.ContactsRepository
import io.swagger.v3.oas.annotations.info.Contact
import org.springframework.stereotype.Service

@Service
class ContactsService(
    private val contactsRepository: ContactsRepository,
    private val userService: UserService
) {
    fun addUserToContacts(userId: Long, contactUser: Long, request: ContactRequest): Contacts? {
        var fromUser = userService.getUserById(userId)
        var toUser = userService.getUserById(contactUser)

        if (fromUser == null || toUser == null) {
            return null
        }

        var firstContacts: Contacts = Contacts()
        firstContacts.user = fromUser
        firstContacts.contact = toUser
        firstContacts.request = request
        contactsRepository.save(firstContacts)

        var secondContacts: Contacts = Contacts()
        secondContacts.user = toUser
        secondContacts.contact = fromUser
        secondContacts.request = request
        contactsRepository.save(secondContacts)

        return firstContacts
    }

    fun getUserContacts(userId: Long): List<Contacts> = contactsRepository.getContactsByUserId(userId)
    fun getContactsByRequestId(requestId: Long) = contactsRepository.getContactsByRequestId(requestId)
}