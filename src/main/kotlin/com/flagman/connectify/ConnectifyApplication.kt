package com.flagman.connectify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConnectifyApplication

fun main(args: Array<String>) {
	runApplication<ConnectifyApplication>(*args)
}
