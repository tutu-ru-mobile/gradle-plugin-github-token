package ru.tutu.log

import java.lang.RuntimeException

object TutuLog {
    private val defaultPlugins = listOf(PrintLogs())
    private var plugins: List<LogPlugin> = defaultPlugins

    fun addPlugin(plugin: LogPlugin) {
        if (plugins == defaultPlugins) {
            plugins = listOf()
        }
        plugins += plugin
    }

    fun debug(message: String) = plugins.forEach { it.debug(message) }
    fun info(message: String) = plugins.forEach { it.info(message) }
    fun warning(message: String) = plugins.forEach { it.warning(message) }
    fun error(message: String, throwable: Throwable? = null) = plugins.forEach {
        it.error(message)
        throwable?.printStackTrace()
    }

    fun fatalError(message: String, throwable: Throwable? = null): Nothing {
        plugins.forEach { it.error("FATAL ERROR: $message") }
        if (throwable != null) {
            throw RuntimeException("FATAL ERROR: $message", throwable)
        } else {
            throw RuntimeException("FATAL ERROR: $message")
        }
    }
}

interface LogPlugin {
    fun debug(message: String)
    fun info(message: String)
    fun warning(message: String)
    fun error(message: String)
}

class PrintLogs : LogPlugin {
    override fun debug(message: String) = println("debug: $message")
    override fun info(message: String) = println("info: $message")
    override fun warning(message: String) = println("Warning: $message")
    override fun error(message: String) = println("ERROR: $message")
}