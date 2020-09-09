package model

import IState
//todo rename rename file to AppState.kt
data class AppState(
    val oAuthTempCode: String? = null,
    val authToken: String? = null,
    val tokenSavedToLocalhost:Boolean = false,
    val screen: Screen? = null
) : IState {
    sealed class Screen {
        data class Error(
            val info: String
        ) : Screen()
    }

}
