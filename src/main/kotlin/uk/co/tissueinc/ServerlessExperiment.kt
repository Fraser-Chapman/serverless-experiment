package uk.co.tissueinc

import uk.co.tissueinc.ServerlessExperimentFnHandler
import uk.co.tissueinc.formats.argoMessage
import uk.co.tissueinc.formats.argoMessageLens
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.filter.DebuggingFilters.PrintResponse
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction
import org.http4k.serverless.AppLoader
import org.http4k.serverless.AwsLambdaEventFunction
import org.http4k.serverless.FnLoader


object ServerlessExperimentAppLoader : AppLoader {
    override fun invoke(env: Map<String, String>): HttpHandler {

        val client = PrintResponse()
            .then(ClientFilters.SetBaseUriFrom(Uri.of("https://pokeapi.co/api/v2/pokemon")))
            .then(JavaHttpClient())

        return PrintRequest()
            .then(
                routes(
                    "/pokemon" bind client,
                    "/ping" bind GET to {
                        Response(OK).body("pong")
                    },

                    "/formats/json/argo" bind GET to {
                        Response(OK).with(argoMessageLens of argoMessage)
                    },

                    "/testing/kotest" bind GET to {request ->
                        Response(OK).body("Echo '${request.bodyString()}'")
                    }
                )
            )
    }
}

class ServerlessExperimentAppFunction : ApiGatewayV1LambdaFunction(ServerlessExperimentAppLoader)

class ServerlessExperimentEventFunction : AwsLambdaEventFunction(FnLoader {
    ServerlessExperimentFnHandler
})
