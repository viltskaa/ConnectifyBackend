package com.flagman.connectify.repositories

import com.flagman.connectify.models.Contacts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ContactsRepository: JpaRepository<Contacts, Long> {
    @Query("select c from Contacts c where c.user.id = :userId")
    fun getContactsByUserId(userId: Long): List<Contacts>
    fun getContactsByRequestId(requestId: Long): List<Contacts>
}