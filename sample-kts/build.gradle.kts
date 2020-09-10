plugins {
    id("ru-tutu-github-token") version "1.0.0"
    kotlin("jvm") version "1.3.72"
}

gitHubToken {
    secretAES = "some_secret_key"
    scope = "read:packages"
    saveToHomeDir = true
}

repositories {
    maven {
        setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin/")//todo uri
        credentials {
            username = "github-user"
            password = gitHubToken.getToken(project)
        }
    }
    google()
    jcenter()
}

dependencies {
    implementation("ru.tutu:lib1:1.2")
}
