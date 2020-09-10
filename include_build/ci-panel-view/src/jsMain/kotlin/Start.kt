import kotlinx.css.*
import lib.dyn
import lib.renderReactMviComponent
import model.Intent
import model.store
import view.ApplicationComponent
import kotlin.browser.document
import kotlin.browser.window

val CLIENT_ID = "decb926d870e0a6ca0d0"//todo вынести в конфигурацию
val AUTH_TOKEN_URL = "https://tutu-ci.herokuapp.com/github_token_localhost"

fun startWeb() {
    val githubRedirectCode = lib.getHrefParam("code")
    if (githubRedirectCode != null) {
        store.dispatch(Intent.ReceiveTempCode(githubRedirectCode))
    } else {
        store.dispatch(Intent.GetScopeFromLocalhost)
    }
    window.history.pushState(dyn {}, document.title, window.location.pathname) // Затираем url search params
    CSSBuilder(allowClasses = false).apply {
        body {
            margin(0.px)
            padding(0.px)
        }
    }
    document.getElementById("react-app")?.renderReactMviComponent<ApplicationComponent>()
}
