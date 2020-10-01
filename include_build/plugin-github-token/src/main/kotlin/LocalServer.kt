import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import ru.tutu.gradle.KotlinSwingStandardColoursEx
import java.io.File

fun main() {//todo delete

}

@OptIn(EngineAPI::class)
fun getGithubToken(gitHubTokenScope: String): String {
    val port: Int = 55555
    return runBlocking {
        val token = suspendCancellableCoroutine<String> { continuation ->
            GlobalScope.launch {
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
                        get("scope") {
                            context.respondText(gitHubTokenScope)
                        }
                        get("savetoken") {
                            val token: String? = context.parameters["token"]
                            if (token != null) {
                                context.respondText("done")
                                continuation.resumeWith(Result.success(token))
                            } else {
                                continuation.resumeWith(Result.failure(Exception("continuation token == null")))
                            }
                            context.application.dispose()
                            cancel()
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
                val message = "Now open browser at: http://localhost:$port"
                println(message)//todo print only localhost

                val frame = KotlinSwingStandardColoursEx(message)
                frame.isVisible = true

            }
        }
        return@runBlocking token
    }
}
