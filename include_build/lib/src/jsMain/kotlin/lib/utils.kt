package lib

import org.w3c.dom.url.URL
import kotlin.browser.window

inline fun getHrefParam(key: String): String? = URL(window.location.href).searchParams.get(key)