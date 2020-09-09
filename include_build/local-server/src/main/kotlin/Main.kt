import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    runStaticWebServer() {}
}

@OptIn(EngineAPI::class)
fun runStaticWebServer(callback: (token: String) -> Unit) {
    val port: Int = 55555
    runBlocking {
        //todo сделать timeout чтобы сборка не зависла на CI/CD
        coroutineScope() {
            val job = this
            var server: BaseApplicationEngine? = null
            server = embeddedServer(if (true) Netty else CIO, port, configure = {
                //конфигурация может быть специфичная для Netty или CIO
                connectionGroupSize
                workerGroupSize
                callGroupSize
                //requestQueueLimit
                //runningLimit
                //shareWorkGroup
            }) {
                routing {
                    get("savetoken") {
                        val token: String? = context.parameters["token"]
                        if (token != null) {
                            context.respondText("done")
                            callback(token)
//                            server?.stop(0, 0)
//                            job.cancel()
                        }
                    }
                    static("/") {
                        if (true) {
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
            }
            server.start(wait = false)
            println("http://localhost:$port")//todo print only localhost
        }
    }
}
