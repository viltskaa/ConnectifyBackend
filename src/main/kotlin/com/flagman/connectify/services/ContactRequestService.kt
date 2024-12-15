package com.flagman.connectify.services

import com.flagman.connectify.models.ContactRequest
import com.flagman.connectify.repositories.ContactRequestRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ContactRequestService(
    private val contactRequestRepository: ContactRequestRepository,
    private val contactsService: ContactsService,
    private val userService: UserService
) {
    fun createContactRequest(fromUserId: Long, toUserId: Long): ContactRequest? {
        var fromUser = userService.getUserById(fromUserId)
        var toUser = userService.getUserById(toUserId)

        if (fromUser == null || toUser == null) {
            return null
        }

        var contactRequest = ContactRequest()
        contactRequest.toUser = toUser
        contactRequest.fromUser = fromUser

        return contactRequestRepository.save(contactRequest)
    }

    fun approveContactRequest(contactRequestId: Long): ContactRequest? {
        var contactRequest = contactRequestRepository.findById(contactRequestId).getOrNull()

        if (contactRequest == null || contactRequest.canceled!! || contactRequest.approved!!) {
            return null
        }

        contactRequest.approved = true
        contactRequest = contactRequestRepository.save(contactRequest)

        contactsService.addUserToContacts(
            contactRequest.fromUser!!.id!!,
            contactRequest.toUser!!.id!!,
            contactRequest
        )
        return contactRequest
    }

    fun cancelContactRequest(contactRequestId: Long): ContactRequest? {
        var contactRequest = contactRequestRepository.findById(contactRequestId).getOrNull()

        if (contactRequest == null) {
            return null
        }

        contactRequest.canceled = true
        return contactRequestRepository.save(contactRequest)
    }

    fun getRequestsByUserId(userId: Long): List<ContactRequest> =
        contactRequestRepository.getAllByToUserId(userId) +
                contactRequestRepository.getAllByFromUserId(userId)
}