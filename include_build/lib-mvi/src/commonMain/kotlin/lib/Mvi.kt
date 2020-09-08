package lib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun <S, E> resultState(state:S, vararg sideEffects:E) = Mvi.ReducerResult<S,E>(
    Mvi.StateResult.UpdateState(state),
    sideEffects.toList()
)

fun <S, E> resultSideEffects(vararg sideEffects:E) = Mvi.ReducerResult<S, E>(
    Mvi.StateResult.SkipState(),
    sideEffects.toList()
)

fun <S, E> doNothing() = resultSideEffects<S, E>()

object Mvi {
    interface Store<State, Intent> {
        fun dispatch(intent: Intent)
        fun subscribeToState(subscription: (State) -> Unit)
        val state: State
    }

    sealed class StateResult<S> {
        class UpdateState<S>(val state: S) : StateResult<S>()
        class SkipState<S> : StateResult<S>()
    }

    class ReducerResult<State, SideEffect>(
        val state: StateResult<State>,
        val sideEffects: List<SideEffect> = emptyList()
    )

    fun <State, Intent, SideEffect> store(
        initialState: State,
        reducer: (State, Intent) -> ReducerResult<State, SideEffect>,
        sideEffectHandler: suspend (Store<State, Intent>, SideEffect) -> Unit
    ): Store<State, Intent> {
        var state: State = initialState
        val subscriptions: MutableList<(State) -> Unit> = mutableListOf()

        return object : Store<State, Intent> {
            override fun dispatch(intent: Intent) {

                fun updateState(st: State) {
                    state = st
                    subscriptions.forEach {
                        it.invoke(state)
                    }
                }

                fun applySideEffect(effect: SideEffect) {
                    val store = this
                    GlobalScope.launch {
                        sideEffectHandler(store, effect)
                    }
                }

                val reduce = reducer(state, intent)
                when (val stateResult = reduce.state) {
                    is StateResult.UpdateState -> {
                        updateState(stateResult.state)
                    }
                }
                reduce.sideEffects.forEach {
                    applySideEffect(it)
                }
            }

            override fun subscribeToState(subscription: (State) -> Unit) {
                subscriptions.add(subscription)
                subscription(state)
            }

            override val state: State get() = state
        }
    }
}

fun <S1, S2, E1, E2> Mvi.ReducerResult<S1, E1>.map(mapState: (S1) -> S2, mapEffect: (E1) -> E2): Mvi.ReducerResult<S2, E2> =
    Mvi.ReducerResult(
        state = when (state) {
            is Mvi.StateResult.UpdateState -> {
                Mvi.StateResult.UpdateState(mapState(state.state))
            }
            is Mvi.StateResult.SkipState -> {
                Mvi.StateResult.SkipState()
            }
        },
        sideEffects = sideEffects.map(mapEffect)
    )
