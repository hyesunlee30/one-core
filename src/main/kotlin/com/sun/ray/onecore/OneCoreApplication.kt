package com.sun.ray.onecore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OneCoreApplication

fun main(args: Array<String>) {
    runApplication<OneCoreApplication>(*args)
}
