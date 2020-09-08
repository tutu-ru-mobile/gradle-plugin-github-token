package network

import lib.Result2

expect suspend fun requestStr(
    hostPath: String,
    urlArgs: Map<String, String> = mapOf(),
    method: Method = Method.GET,
    headers: Map<String, String> = mapOf(),
    body: String? = null
): Result2<String>

enum class Method {
    GET,
    POST
}
