package lib

fun dyn(lambda: dynamic.() -> Unit): Any {
    val result = js("{}")
    lambda(result)
    return result as Any
}

fun dynArr(count: Int, lambda: dynamic.(index: Int) -> Unit): List<Any> =
    List(count) { index ->
        dyn {
            lambda(this@dyn, index)
        }
    }
