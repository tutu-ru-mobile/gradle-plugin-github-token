package lib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.promise
import kotlin.test.Test
import kotlin.test.assertEquals

class DynamicTest {

    @Test
    fun sampleTest() {
        println("test sample")
        val dynObject: Any = dyn {
            a = dynArr(5) {
                b = it
            }
        }

        val jsonStr: String = JSON.stringify(dynObject)
        println(jsonStr)
        assertEquals(
            """{"a":[{"b":0},{"b":1},{"b":2},{"b":3},{"b":4}]}""",
            jsonStr
        )
//        assertEquals(1,2)
    }

    @Test
    fun promiseTest() = runTest {
        repeat(10) {
            println("$it\n")
            delay(10)
        }
    }

}

//https://youtrack.jetbrains.com/issue/KT-22228#focus=streamItem-27-3964713.0-0
fun runTest(block: suspend () -> Unit): dynamic =
    GlobalScope.promise { block() }


