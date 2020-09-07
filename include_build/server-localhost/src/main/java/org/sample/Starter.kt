package org.sample

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

suspend fun main() {
  GlobalScope.launch {
    println("hello coroutines")
  }
  println("hello starter")
  val port = 8080
  startSimpleServer(port)
  val serverResponse = HttpClient(Apache).get<String>("http://localhost:$port/healthz")
  println("server say: ${serverResponse}")
}
