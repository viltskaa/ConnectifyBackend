package com.flagman.connectify.controllers

import com.flagman.connectify.dto.UserDto
import com.flagman.connectify.dto.toUserDto
import com.flagman.connectify.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsersByQuery(@RequestParam username: String): List<UserDto> {
        return userService.getUsersByQuery(username).map { it -> toUserDto(it, null) }
    }
}