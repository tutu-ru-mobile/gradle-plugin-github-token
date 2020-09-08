package lib

import org.w3c.dom.Element
import react.*
import react.dom.render

abstract class MviComponent<St, In>(
    val store: Mvi.Store<St, In>,
    val render2: RBuilder.(St, (In) -> Unit) -> Unit
) : RComponent<RProps, St>()
        where St : RState {

    init {
        store.subscribeToState { newState ->
            setState(transformState = { newState })
        }
        state = store.state
    }

    override fun componentDidMount() {
    }

    override fun RBuilder.render() {
        render2(state) {
            store.dispatch(it)
        }
    }
}

inline fun <reified Component> Element.renderReactMviComponent()
        where Component : RComponent<out RProps, out RState> {
    render(
        buildElement {
            childList.add(createElement(Component::class.js, js("{}")))
        },
        this
    )
}
