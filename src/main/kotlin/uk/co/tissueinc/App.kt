package uk.co.tissueinc

import org.http4k.server.SunHttp
import org.http4k.server.asServer

val app = ServerlessExperimentAppLoader(emptyMap())

fun main() {
    val server = app.asServer(SunHttp(8000)).start()
    println("Server started on port ${server.port()}")
}