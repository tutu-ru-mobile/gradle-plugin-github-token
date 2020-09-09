package model

sealed class Intent {
    object ReloadPage : Intent()
    class ReceiveTempCode(val code: String) : Intent()
    class TokenLoaded(val token: String) : Intent()
    class Error(val throwable: Throwable) : Intent()
}

sealed class AppSideEffect {
    object ReloadPage : AppSideEffect()
    class LoadToken(val tempCode: String) : AppSideEffect()
    class SaveTokenToLocalhost(val token: String) : AppSideEffect()
}
