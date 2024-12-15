package com.flagman.connectify.controllers

import com.flagman.connectify.dto.ContactDto
import com.flagman.connectify.dto.MessageDto
import com.flagman.connectify.dto.RequestApproveDto
import com.flagman.connectify.dto.RequestCancelDto
import com.flagman.connectify.dto.RequestCreateDto
import com.flagman.connectify.dto.RequestDto
import com.flagman.connectify.dto.toContactDto
import com.flagman.connectify.dto.toRequestDto
import com.flagman.connectify.services.ContactRequestService
import com.flagman.connectify.services.ContactsService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class ContactsController(
    private val requestService: ContactRequestService,
    private val contactsService: ContactsService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/createRequest/{receiverId}")
    fun createRequest(requestCreateDto: RequestCreateDto): RequestDto? {
        val request = requestService.createContactRequest(
            requestCreateDto.fromUser,
            requestCreateDto.toUser
        )

        if (request == null) {
            return null
        }

        val requestDto = toRequestDto(request)

        messagingTemplate.convertAndSend("/topic/requests/${requestCreateDto.fromUser}", requestDto)
        messagingTemplate.convertAndSend("/topic/requests/${requestCreateDto.toUser}", requestDto)

        return requestDto
    }

    @MessageMapping("/cancelRequest/{receiverId}")
    fun deleteRequest(request: RequestCancelDto): RequestDto? {
        val request = requestService.cancelContactRequest(request.requestId)

        if (request == null) {
            return null
        }

        val requestDto = toRequestDto(request)
        messagingTemplate.convertAndSend("/topic/cancelRequest/${request.toUser!!.id!!}", requestDto)
        messagingTemplate.convertAndSend("/topic/cancelRequest/${request.fromUser!!.id!!}", requestDto)

        return requestDto
    }

    @MessageMapping("/approveRequest/{receiverId}")
    fun approveRequest(request: RequestApproveDto): RequestDto? {
        val request = requestService.approveContactRequest(request.requestId)

        if (request == null) {
            return null
        }

        val requestDto = toRequestDto(request)
        messagingTemplate.convertAndSend(
            "/topic/approveRequest/${request.toUser!!.id!!}",
            requestDto
        )
        messagingTemplate.convertAndSend(
            "/topic/approveRequest/${request.fromUser!!.id!!}",
            requestDto
        )

        val newContacts = contactsService.getContactsByRequestId(request.id!!)
        val toUserContact = newContacts.find { it -> it.user!!.id!! == request.toUser!!.id!! }
        val fromUserContact = newContacts.find { it -> it.user!!.id!! == request.fromUser!!.id!! }

        if (toUserContact == null || fromUserContact == null) {
            return null
        }

        messagingTemplate.convertAndSend(
            "/topic/addContact/${request.toUser!!.id!!}",
            toContactDto(toUserContact)
        )
        messagingTemplate.convertAndSend(
            "/topic/addContact/${request.fromUser!!.id!!}",
            toContactDto(fromUserContact)
        )

        return requestDto
    }

    @SubscribeMapping("/historyRequests/{userId}")
    fun sendChatHistory(@DestinationVariable userId: Long): List<RequestDto?> {
        return requestService.getRequestsByUserId(userId).map { it -> toRequestDto(it) };
    }

    @SubscribeMapping("/contacts/{userId}")
    fun userContacts(@DestinationVariable userId: Long): List<ContactDto> {
        return contactsService.getUserContacts(userId).map { it -> toContactDto(it) }
    }
}