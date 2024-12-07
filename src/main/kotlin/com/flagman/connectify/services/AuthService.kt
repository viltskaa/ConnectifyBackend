package com.flagman.connectify.services

import com.flagman.connectify.jwt.JWT
import com.flagman.connectify.jwt.JwtConfig
import com.flagman.connectify.models.User
import com.flagman.connectify.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

@Service
class AuthService(
    private var userService: UserService,
    private var passwordEncoder: PasswordEncoder,
    private var jwt: JWT
) {
    fun register(username: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        return userService.registerUser(username, encodedPassword)
    }

    fun generateToken(username: String): String = jwt.generate(username)
    fun validateToken(token: String): Boolean = jwt.validate(token)
    fun getUsernameFromToken(token: String): String = jwt.getUsernameFromToken(token)
}