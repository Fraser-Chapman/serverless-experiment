package uk.co.tissueinc

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction
import org.http4k.serverless.AppLoader
import org.http4k.serverless.AwsLambdaEventFunction
import org.http4k.serverless.FnLoader
import uk.co.tissueinc.formats.argoMessageLens
import uk.co.tissueinc.formats.exampleMessage

object ServerlessExperimentAppLoader : AppLoader {
    override fun invoke(env: Map<String, String>): HttpHandler {

        return PrintRequest()
            .then(
                routes(
                    "/example" bind GET to {
                        Response(OK).with(argoMessageLens of exampleMessage)
                    }
                )
            )
    }
}

class ServerlessExperimentAppFunction : ApiGatewayV1LambdaFunction(ServerlessExperimentAppLoader)

class ServerlessExperimentEventFunction : AwsLambdaEventFunction(FnLoader {
    ServerlessExperimentFnHandler
})
