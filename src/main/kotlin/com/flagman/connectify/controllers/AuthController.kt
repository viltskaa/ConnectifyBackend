package com.flagman.connectify.controllers

import com.flagman.connectify.dto.UserDto
import com.flagman.connectify.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signUp")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        authService.register(
            request.username,
            request.password,
            request.email,
            request.bio
        )
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/signIn")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<UserDto> {
        val user = authService.generateTokenAndGetUser(request.username)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/verify")
    fun verifyToken(): ResponseEntity<Boolean> =
        ResponseEntity.ok(true)

    @GetMapping("/exist")
    fun usernameExist(@RequestParam username: String): ResponseEntity<Boolean> =
        ResponseEntity.ok(authService.existsUsername(username))
}

data class RegisterRequest(val username: String, val password: String, val email: String, val bio: String)
data class LoginRequest(val username: String, val password: String)