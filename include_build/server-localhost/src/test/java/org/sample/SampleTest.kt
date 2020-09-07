package org.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class SampleTest() {
  @Test
  fun sampleTest() {
    println("test")
    assertEquals(1, 1)
  }

  @Test
  fun testCoroutine() = runBlocking {
    delay(1)
    println("test coroutines")
    Unit
  }

}
