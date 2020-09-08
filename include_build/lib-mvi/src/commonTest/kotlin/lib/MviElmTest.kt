package lib

import kotlin.test.Test
import kotlin.test.assertEquals

class MviElmTests {

    @Test
    fun testInitState() {
        val store = Mvi.store<Int, Unit, Unit>(0, { _, _ ->
            doNothing()
        }, { _, _ -> })
        var initState = -1
        store.subscribeToState {
            initState = it
        }
        assertEquals(0, initState)
    }

    @Test
    fun testActionPropagation() {
        val store = Mvi.store<Long, Int, Unit>(0, { state, action ->
            resultState(state + action)
        }, { _, _ -> })
        store.dispatch(1)
        store.dispatch(2)
        assertEquals(3L, store.state)
    }

}
