package lib

import kotlin.test.Test
import kotlin.test.assertEquals

class ReduxTests {

    @Test
    fun testInitState() {
        val store = Redux.store<Int, Unit>(0) { _, _ -> throw Exception() }
        var initState = -1
        store.subscribeToState {
            initState = it
        }
        assertEquals(0, initState)
    }

    @Test
    fun testActionPropagation() {
        val store = Redux.store<Long, Int>(0) { state: Long, action: Int ->
            state + action
        }
        store.dispatch(1)
        store.dispatch(2)
        assertEquals(3L, store.state)
    }

    @Test
    fun testMiddleWareNext() {
        val middleware: MiddleWare<Long, Int> = { store, next, action ->
            next(action)
        }
        println("before store")
        val store = Redux.store<Long, Int>(0, middleware) { state: Long, action: Int ->
            state + action
        }
        println("before dispatch")
        store.dispatch(1)
        assertEquals(1L, store.state)
    }

    @Test
    fun testMiddleWareSkip() {
        val middleware: MiddleWare<Long, Int> = { store, next, action ->

        }
        println("before store")
        val store = Redux.store<Long, Int>(0, middleware) { state: Long, action: Int ->
            state + action
        }
        println("before dispatch")
        store.dispatch(1)
        assertEquals(0L, store.state)
    }

}
