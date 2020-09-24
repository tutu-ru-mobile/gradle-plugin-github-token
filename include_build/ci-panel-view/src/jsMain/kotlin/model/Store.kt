package model

import AUTH_TOKEN_URL
import CLIENT_ID
import lib.Mvi
import network.Method
import network.requestStr
import kotlin.browser.window

val store = Mvi.store<AppState, Intent, AppSideEffect>(
    initialState = AppState(),
    reducer = { state, intent ->
        ciPanelReducer(state, intent)
    }, sideEffectHandler = { store, effect ->
        @Suppress("IMPLICIT_CAST_TO_ANY")
        when (effect) {
            is AppSideEffect.ReloadPage -> {
                window.location.reload()
            }
            is AppSideEffect.GetScopeFromLocalhost -> {
                val response = requestStr(
                    "http://localhost:55555/scope",//todo simplify
                    mapOf(),
                    method = Method.GET
                )
                response.onSuccess {
                    store.dispatch(Intent.ReceiveScope(it))
                }
                response.onFailure {
                    store.dispatch(Intent.Error(Exception("get scope from localhost error")))
                }
            }
            is AppSideEffect.LoadToken -> {
                requestStr(
                    AUTH_TOKEN_URL,
                    mapOf(
                        "client_id" to CLIENT_ID,
                        "code" to effect.tempCode
                    )
                ).onSuccess {
                    store.dispatch(Intent.TokenLoaded(it))
                }.onFailure {
                    store.dispatch(Intent.Error(it))
                }
            }
            is AppSideEffect.SaveTokenToLocalhost -> {
                val response = requestStr(
                    "http://localhost:55555/savetoken",//todo simplify
                    mapOf("token" to effect.token),
                    method = Method.GET
                )
                response.onSuccess {
                    store.dispatch(Intent.TokenSaved(it))
                }
                response.onFailure {
                    store.dispatch(Intent.Error(Exception("save token error")))
                }
            }
        }
    }
)
