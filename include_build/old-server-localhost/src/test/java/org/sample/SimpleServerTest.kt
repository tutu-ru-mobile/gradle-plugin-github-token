package org.sample

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class SimpleServerTest() {
  private val httpClient = HttpClient(Apache)

  @Test
  fun sampleTest() {
    runBlocking {
      val port = 8081
      startSimpleServer(8081)
      assertEquals("ok", httpClient.get<String>("http://localhost:$port/healthz"))
    }
  }
}
