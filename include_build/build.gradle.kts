plugins {
    id("org.jetbrains.kotlin.multiplatform") version KOTLIN_VERSION apply false
}

allprojects {
    version = "1.0"
    repositories {
        jcenter()//todo mavenCentral
    }
}
