package com.flagman.connectify.repositories

import com.flagman.connectify.models.ContactRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContactRequestRepository: JpaRepository<ContactRequest, Long> {
    fun getAllByToUserId(toUserId: Long): List<ContactRequest>
    fun getAllByFromUserId(fromUserId: Long): List<ContactRequest>
}