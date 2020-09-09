import java.util.*
import java.util.Properties
import org.gradle.api.Project

fun Project.getVar(env:String, key: String): String {
    val envVar:String? = System.getenv(env)
    println("$env: $envVar")
    if(envVar != null) {
        println("envVar != null")
        return@getVar envVar
    } else {
        println("else")
        val properties = Properties()
        val propertiesFile = project.rootProject.file("local.properties")
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
            val property: String? = properties.getProperty(key)
            if (property != null) {
                return property
            } else {
                return "todo"//todo
                throw Error("property $key not exists")
            }
        } else {
//        return "todo_error"//todo
            throw Error("local.properties not exists")
        }
    }
}
