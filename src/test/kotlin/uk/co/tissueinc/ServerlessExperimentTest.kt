package uk.co.tissueinc

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.junit.jupiter.api.Test

class ServerlessExperimentTest {

    @Test
    fun `Example endpoint test`() {
        val expectedBody = """
            {"thisIsAString":"stringValue","thisIsANumber":12345,"thisIsAList":[true],"thisIsAnObject":{"thisIsAnotherString":"anotherString"}}
        """.trimIndent()

        val response = app(Request(GET, "/example"))

        response shouldHaveStatus OK
        response shouldHaveBody expectedBody
    }

}
