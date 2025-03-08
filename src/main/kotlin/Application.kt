package com.practice

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Enable CORS to allow frontend access
    install(CORS) {
        anyHost()
    }

    // Other configurations (assuming these are already defined elsewhere)
    configureSerialization()
    configureRouting()
}
