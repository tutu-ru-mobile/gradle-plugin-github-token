import java.util.*
import ru.tutu.gradle.*

plugins {
    id("ru-tutu-github-token") version "1.0.0"
    kotlin("jvm") version "1.3.72"
}

gitHubToken {
    secretAES = "some_secret_key"
    scope = "read:packages"
}

fun getVar2(env: String): String {
    val envVar: String? = System.getenv(env)
    if (envVar != null) {
        return@getVar2 envVar
    } else {
        return gitHubToken.getToken(project)
    }
}

repositories {
    maven {
        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin/")//todo uri
        credentials {
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
