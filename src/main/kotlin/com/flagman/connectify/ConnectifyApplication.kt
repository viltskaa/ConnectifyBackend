package com.flagman.connectify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableConfigurationProperties
class ConnectifyApplication

fun main(args: Array<String>) {
    runApplication<ConnectifyApplication>(*args)
}
