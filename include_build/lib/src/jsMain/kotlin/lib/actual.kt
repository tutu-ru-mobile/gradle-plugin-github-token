package lib

import kotlin.browser.window

actual val IS_LOCALHOST = window.location.hostname == "localhost"
