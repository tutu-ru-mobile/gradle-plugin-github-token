import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.*
import io.ktor.client.response.readText
import io.ktor.client.statement.*
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.*
import io.ktor.response.header
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import java.io.File

@OptIn(EngineAPI::class)
fun main() {
    val port = 55555
    embeddedServer(if (true) Netty else CIO, port, configure = {
        //конфигурация может быть специфичная для Netty или CIO
        connectionGroupSize
        workerGroupSize
        callGroupSize
//    requestQueueLimit
//    runningLimit
//    shareWorkGroup
    }) {
        routing {
            static("/") {
                if(true) {
                    resources("")
                    defaultResource("index.html")
                } else {
                    staticRootFolder = listOf(
                            File("client/build/distributions"),
                            File("../client/build/distributions")
                    ).first { it.exists() }
                    files(".")
                    default("index.html")
                }
            }
        }
    }.start(wait = false)
    println("http://localhost:$port")//todo print only localhost
}
