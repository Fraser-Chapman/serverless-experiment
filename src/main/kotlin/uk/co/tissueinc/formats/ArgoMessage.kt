package uk.co.tissueinc.formats

import org.http4k.core.Body
import org.http4k.format.Argo.array
import org.http4k.format.Argo.boolean
import org.http4k.format.Argo.json
import org.http4k.format.Argo.number
import org.http4k.format.Argo.obj
import org.http4k.format.Argo.string

val argoMessageLens = Body.json().toLens()

val exampleMessage = obj(
    "thisIsAString" to string("stringValue"),
    "thisIsANumber" to number(12345),
    "thisIsAList" to array(listOf(boolean(true))),
    "thisIsAnObject" to obj(
        "thisIsAnotherString" to string("anotherString")
    )
)
