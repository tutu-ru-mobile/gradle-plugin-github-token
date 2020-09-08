package network

import kotlinx.coroutines.await
import lib.Result2
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.js.json

actual suspend fun requestStr(
    hostPath: String,
    urlArgs: Map<String, String>,
    method: Method,
    headers: Map<String, String>,
    body: String?
): Result2<String> {
    var url: String = hostPath
    if (urlArgs.isNotEmpty()) {
        url += "?"
        url += urlArgs.entries.joinToString("&", transform = { "${it.key}=${urlEncode(it.value)}" })
    }
    val response = window.fetch(
        url,
        RequestInit(
            method = method.toString(),
            headers = json().apply {
                headers.entries.forEach {
                    set(it.key, it.value)
                }
            },
            body = body ?: undefined
        )
    ).await()
    return if (response.ok) {
        Result2.success(response.text().await())
    } else {
        Result2.failure(Exception(response.statusText))
    }
}

@JsName("encodeURIComponent")
external fun urlEncode(value: String): String

@JsName("decodeURIComponent")
external fun urlDecode(encoded: String): String
