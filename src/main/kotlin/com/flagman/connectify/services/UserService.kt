package com.flagman.connectify.services

import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(username: String, password: String, email: String): User {
        val user = User(username, password, email)
        return userRepository.save(user)
    }
    fun getUserByUsername(username: String): User? = userRepository.findOneByUsername(username)
    fun getUserById(id: Long): User? = userRepository.findUserById(id)
    fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)
}