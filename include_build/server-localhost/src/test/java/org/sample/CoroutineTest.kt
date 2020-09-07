package org.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CoroutineTest {
  @Test
  fun sampleTest() {
    runBlockingTest {
      val waitHours = 10
      println("too long to wait $waitHours hours")
      repeat(waitHours) {
        println("wait $it hour")
        delay(60 * 60 * 1000)//delay 1 hour
      }
      println("spend $waitHours hours")
    }
  }
}
