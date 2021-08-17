import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import ru.tutu.gradle.Form
import ru.tutu.gradle.openFrameWithCopyText
import java.io.File

@OptIn(EngineAPI::class)
fun getGithubToken(gitHubTokenScope: String, showInfoForm:Boolean = true): String {
    val port: Int = 4321
    return runBlocking {
        var infoForm: Form? = null
        var server: BaseApplicationEngine? = null
        val token = suspendCancellableCoroutine<String> { continuation ->
            GlobalScope.launch {
                server = embeddedServer(Netty, port, configure = {
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
                server?.start(wait = false)
                val urlText = "http://localhost:$port"
                val urlDescription = "Now open browser at:"
                val message = "$urlDescription $urlText"
                println(message)//todo print only localhost

                if(showInfoForm) {
                    infoForm = openFrameWithCopyText("auth", "$urlDescription ", urlText)
                }

            }
        }
        infoForm?.close()
        server?.stop(0, 0)
        return@runBlocking token
    }
}
