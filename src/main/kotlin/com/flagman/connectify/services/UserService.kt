package com.flagman.connectify.services

import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(username: String, password: String): User {
        val user = User(username, password)
        return userRepository.save(user)
    }
}