package nl.openvalue.paulienvanalst.kotlin.ktor.coroutines

import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::recipes
    ).start(wait = true)
}


fun Application.recipes() {
    println("Starting up!")
}
