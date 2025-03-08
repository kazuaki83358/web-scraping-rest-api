package com.practice

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/songs") {
            val songs = scrapeSongs()  // Call the web scraper function
            call.respond(songs)  // Return data as JSON
        }
    }
}
