package uk.co.tissueinc

import org.http4k.server.SunHttp
import org.http4k.server.asServer

val app = ServerlessExperimentAppLoader(emptyMap())

fun main() {
    app.asServer(SunHttp(8000)).start()
}
