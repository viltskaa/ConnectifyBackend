package com.flagman.connectify.controllers

import com.flagman.connectify.services.MistralAiService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/ai")
class AiController(
    private val mistralAiService: MistralAiService
) {
    @PostMapping("/get")
    fun getAi(@RequestBody message: AiMessage): String =
        mistralAiService.getResponse(message.text)

    @PostMapping("/direct")
    fun directAi(@RequestBody message: AiMessage): String =
        mistralAiService.getDirectResponse(message.text)
}

data class AiMessage(val text: String)