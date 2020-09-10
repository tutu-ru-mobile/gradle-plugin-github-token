package view

import CLIENT_ID
import github.GitHub
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import model.AppState
import model.Intent
import react.RBuilder
import react.dom.*
import styled.css
import styled.styledDiv

fun RBuilder.renderAppState(state: AppState, userIntent: (Intent) -> Unit) {
    styledDiv {
        css {
            padding(32.px, 16.px)
//                transform { scale(2.0) }
        }
        div {
            when (state.screen) {
                is AppState.Screen.Error -> {
                    +"Something go wrong, info: ${state.screen.info}"
                    button {
                        +"reload page"
                        attrs.onClickFunction = {
                            userIntent(Intent.ReloadPage)
                        }
                    }
                }
                else -> {
                    if (state.oAuthTempCode == null && state.tokenScope != null) {
                        button {
                            attrs {
                                +"OAuth with GitHub"
                                onClickFunction = {
                                    GitHub.auth(CLIENT_ID, state.tokenScope)
                                }
                            }
                        }
                        //a(href = href) { +"OAuth with GitHub 2" }
                    } else if (state.authToken != null && state.tokenSavedToLocalhost == true) {
                        h2 { +"Done!" }
                        br {}
                        +"GitHub token saved"
                        br {}
                        +"Now you may close browser"
                    } else {
                        +"Loading..."
                        br { }
                        img(src = "loading.gif") {}
                    }
                }
            }

        }
    }
}

