package model

import lib.*

fun ciPanelReducer(
    state: AppState,
    intent: Intent
): Mvi.ReducerResult<AppState, AppSideEffect> =
    when (intent) {
        is Intent.GetScopeFromLocalhost -> {
            resultSideEffects(
                AppSideEffect.GetTokenFromLocalhost
            )
        }
        is Intent.ReceiveScope -> {
            resultState(
                state.copy(
                    tokenScope = intent.scope
                )
            )
        }
        is Intent.ReceiveTempCode -> {
            resultState(
                state.copy(
                    oAuthTempCode = intent.code
                ),
                AppSideEffect.LoadToken(intent.code)
            )
        }
        is Intent.TokenLoaded -> {
            resultState(
                    state.copy(
                            authToken = intent.token
                    ),
                    AppSideEffect.SaveTokenToLocalhost(intent.token)
            )
        }
        is Intent.TokenSaved -> {
            if (intent.response == "done") {
                resultState(
                        state.copy(
                            tokenSavedToLocalhost = true
                        )
                )
            } else {
                resultState(
                        state.copy(
                                screen = AppState.Screen.Error("token save response != done")
                        )
                )
            }
        }
        is Intent.Error -> {
            resultState(
                    state.copy(
                            screen = AppState.Screen.Error(with(intent.throwable) { "$cause, $message" })
                    )
            )
        }
        is Intent.ReloadPage -> {
            resultSideEffects(AppSideEffect.ReloadPage)
        }
    }
