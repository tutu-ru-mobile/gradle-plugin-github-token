plugins {
    kotlin("jvm") version embeddedKotlinVersion
}

repositories {
    jcenter()
}

dependencies {

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}
