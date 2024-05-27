package uk.co.tissueinc

import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ClientFilters
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.filter.DebuggingFilters.PrintResponse
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction
import org.http4k.serverless.AppLoader
import org.http4k.serverless.AwsLambdaEventFunction
import org.http4k.serverless.FnLoader
import uk.co.tissueinc.formats.argoMessage
import uk.co.tissueinc.formats.argoMessageLens

object ServerlessExperimentAppLoader : AppLoader {
    override fun invoke(env: Map<String, String>): HttpHandler {

        val client = PrintResponse()
            .then(ClientFilters.SetBaseUriFrom(Uri.of("https://pokeapi.co/api/v2/pokemon")))
            .then(JavaHttpClient())

        return PrintRequest()
            .then(
                routes(
                    "/formats/json/argo" bind GET to {
                        Response(OK).with(argoMessageLens of argoMessage)
                    }
                )
            )
    }
}

class ServerlessExperimentAppFunction : ApiGatewayV1LambdaFunction(ServerlessExperimentAppLoader)

class ServerlessExperimentEventFunction : AwsLambdaEventFunction(FnLoader {
    ServerlessExperimentFnHandler
})
