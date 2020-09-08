import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    runStaticServer()
}

@OptIn(EngineAPI::class)
fun runStaticServer(port: Int = 55555) {
    runBlocking {
        //todo сделать timeout чтобы сборка не зависла на CI/CD
        coroutineScope {
            embeddedServer(if (true) Netty else CIO, port, configure = {
                //конфигурация может быть специфичная для Netty или CIO
                connectionGroupSize
                workerGroupSize
                callGroupSize
                //requestQueueLimit
                //runningLimit
                //shareWorkGroup
            }) {
                routing {
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
            }.start(wait = false)
            println("http://localhost:$port")//todo print only localhost
        }
    }
}
