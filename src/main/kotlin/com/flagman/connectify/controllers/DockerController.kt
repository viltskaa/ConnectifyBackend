package com.flagman.connectify.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/docker")
class DockerController {
    @GetMapping("/")
    fun helloWorld(): String {
        return "Hello World"
    }
}