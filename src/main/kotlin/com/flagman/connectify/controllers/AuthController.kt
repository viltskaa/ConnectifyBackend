package com.flagman.connectify.controllers

import com.flagman.connectify.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signUp")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        authService.register(request.username, request.password)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/signIn")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val token = authService.generateToken(request.username)
        return ResponseEntity.ok(token)
    }
}

data class RegisterRequest(val username: String, val password: String)
data class LoginRequest(val username: String, val password: String)