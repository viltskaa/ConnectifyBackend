package com.flagman.connectify.services

import com.flagman.connectify.dto.UserDto
import com.flagman.connectify.dto.toUserDto
import com.flagman.connectify.jwt.JWT
import com.flagman.connectify.models.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private var userService: UserService,
    private var passwordEncoder: PasswordEncoder,
    private var jwt: JWT
) {
    fun register(username: String, password: String, email: String, bio: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        return userService.registerUser(username, encodedPassword, email, bio)
    }

    fun generateToken(username: String): String = jwt.generate(username)
    fun generateTokenAndGetUser(username: String): UserDto? {
        var token = generateToken(username)
        val user = getUserFromToken(token)

        if (user == null) {
            return null
        }

        return toUserDto(user, token)
    }

    fun getUsernameFromToken(token: String): String = jwt.getUsernameFromToken(token)
    fun getUserFromToken(token: String): User? {
        val username = getUsernameFromToken(token)
        return userService.getUserByUsername(username)
    }
    fun existsUsername(username: String): Boolean = userService.existsByUsername(username)
}