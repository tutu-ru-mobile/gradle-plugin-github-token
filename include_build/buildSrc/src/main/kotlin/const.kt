import java.util.*
import java.util.Properties
import org.gradle.api.Project

val COROUTINES_VERSION = "1.3.7"
val KT_WRAPPER_V = "pre.102-kotlin-1.3.72"
val REACT_KT_V = "16.13.1"
val KTOR_VERSION = "1.4.1"
val PLUGIN_KOTLIN_VERSION = "1.3.72"
val KOTLIN_VERSION_CLI = "1.5.21"
val KOTLIN_VERSION = if (System.getenv("BUILD_JAR_CLI") == "true") {
    println("use KOTLIN_VERSION_CLI")
    KOTLIN_VERSION_CLI
} else {
    println("use PLUGIN_KOTLIN_VERSION")
    PLUGIN_KOTLIN_VERSION
}
val SERIALIZATION_VERSION = "1.2.2"
