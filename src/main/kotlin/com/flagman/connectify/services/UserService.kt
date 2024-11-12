package com.flagman.connectify.services

import com.flagman.connectify.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
}