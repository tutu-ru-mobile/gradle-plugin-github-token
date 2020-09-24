package model

sealed class Intent {
    object ReloadPage : Intent()
    object GetScopeFromLocalhost : Intent()
    class ReceiveScope(val scope: String) : Intent()
    class ReceiveTempCode(val code: String) : Intent()
    class TokenLoaded(val token: String) : Intent()
    class TokenSaved(val response: String) : Intent()
    class Error(val throwable: Throwable) : Intent()
}

sealed class AppSideEffect {
    object ReloadPage : AppSideEffect()
    object GetScopeFromLocalhost : AppSideEffect()
    class LoadToken(val tempCode: String) : AppSideEffect()
    class SaveTokenToLocalhost(val token: String) : AppSideEffect()
}
