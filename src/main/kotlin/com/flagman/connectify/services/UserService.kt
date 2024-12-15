package com.flagman.connectify.services

import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(username: String, password: String, email: String, bio: String): User {
        val user = User(username, password, email, bio)
        return userRepository.save(user)
    }

    fun setUserOnline(username: String) {
        var user = userRepository.findOneByUsername(username)
        if (user == null) {
            return
        }
        user.online = true
        userRepository.save(user)
    }

    fun setUserOffline(username: String) {
        var user = userRepository.findOneByUsername(username)
        if (user == null) {
            return
        }
        user.online = false
        user.lastSeen = System.currentTimeMillis()

        userRepository.save(user)
    }

    fun getUsersByQuery(query: String): List<User> = userRepository.findAllByUsernameContainsIgnoreCase(query)
    fun getUserByUsername(username: String): User? = userRepository.findOneByUsername(username)
    fun getUserById(id: Long): User? = userRepository.findUserById(id)
    fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)
}