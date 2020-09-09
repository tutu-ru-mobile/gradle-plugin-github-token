import java.util.*

plugins {
    id("ru-tutu-github-package") version "1.0.0"
    kotlin("jvm") //version "1.3.72"
}

//tutuGithubPackages {
//    repo("https://maven.pkg.github.com/tutu-ru-mobile/android-core/")
//}

fun getLocalProp(key: String): String {
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
        throw Error("local.properties not exists")
    }
}

repositories {
    maven {
//        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/android-core/")
        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin/")
        credentials {
            //https://github.com/settings/tokens
            username = getVar("MAVEN_USER", "github.user")//getVar("MAVEN_USER", "mavenUser")
            password = getVar("MAVEN_PASSWORD", "github.packageToken")//getVar("MAVEN_PASSWORD", "mavenPassword")
        }
    }
    google()
    jcenter()
}

dependencies {
    implementation("ru.tutu:lib1:1.2")
}
