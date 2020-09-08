package lib

typealias MiddleWare<S, A> = (store: Redux.Store<S, A>, next: (A) -> Unit, action: A) -> Unit

object Redux {

    interface Store<S, A> {
        fun dispatch(action: A)
        fun subscribeToState(subscription: (S) -> Unit)
        val state: S
    }

    fun <S, A> store(initialState: S, vararg middlewares: MiddleWare<S, A>, reducer: (S, A) -> S): Store<S, A> {
        var state: S = initialState
        val subscriptions: MutableList<(S) -> Unit> = mutableListOf()

        var firstMiddleWare: (A) -> Unit = {
            state = reducer(state, it)
            subscriptions.forEach {
                it.invoke(state)
            }
        }

        val store = object : Store<S, A> {
            override fun dispatch(action: A) {
                firstMiddleWare(action)
            }

            override fun subscribeToState(subscription: (S) -> Unit) {
                subscriptions.add(subscription)
                subscription(state)
            }

            override val state: S get() = state
        }

        val iterator = middlewares.reversed().iterator()
        while (iterator.hasNext()) {
            val middleware = iterator.next()
            val previous = firstMiddleWare
            firstMiddleWare = {
                middleware.invoke(store, previous, it)
            }
        }

        return store
    }

}
