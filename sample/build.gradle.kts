import java.util.*
import ru.tutu.gradle.*

plugins {
    id("ru-tutu-github-token") version "1.0.0"
    kotlin("jvm") version "1.3.72"
}

tutu {
    configVar1 = "sample value"
}

//tutuGithubPackages {
//    repo("https://maven.pkg.github.com/tutu-ru-mobile/android-core/")
//}

fun getVar2(env: String): String {
    val envVar: String? = System.getenv(env)
    if (envVar != null) {
        return@getVar2 envVar
    } else {
        return tutu.getToken(project)
    }
}

repositories {
    maven {
//        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/android-core/")
        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin/")
        credentials {
            //https://github.com/settings/tokens
            username = "github-user"
            password = getVar2("MAVEN_PASSWORD")
        }
    }
    google()
    jcenter()
}

dependencies {
    implementation("ru.tutu:lib1:1.2")
}
