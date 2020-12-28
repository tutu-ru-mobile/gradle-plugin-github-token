import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import java.io.File

fun main() {//todo delete

}

@OptIn(EngineAPI::class)
fun runStaticWebServer(gitHubTokenScope:String, callback: (token: String) -> Unit) {
    //OLD
}
