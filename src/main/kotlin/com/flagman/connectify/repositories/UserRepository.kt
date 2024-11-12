package com.flagman.connectify.repositories

import com.flagman.connectify.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findOneByUserName(userName: String): User?
}