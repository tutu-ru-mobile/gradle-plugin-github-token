package model

import lib.*

fun ciPanelReducer(
    state: AppState,
    intent: Intent
): Mvi.ReducerResult<AppState, AppSideEffect> =
    when (intent) {
        is Intent.TokenLoaded -> {
            println("token: ${intent.token}")//todo
            resultState(
                state.copy(
                    authToken = intent.token
                )
            )
        }
        is Intent.Error -> {
            resultState(
                state.copy(
                    screen = AppState.Screen.Error(with(intent.throwable) { "$cause, $message" })
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
        is Intent.ReloadPage -> {
            resultSideEffects(AppSideEffect.ReloadPage)
        }
    }
