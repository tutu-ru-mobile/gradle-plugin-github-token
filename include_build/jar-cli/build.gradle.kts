import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("plugin.serialization") version KOTLIN_VERSION
}

group = "ru.tutu"
version = "1.0.0"

repositories {
    jcenter()//todo mavenCentral()
}

application {
    mainClassName = "ru.tutu.token.JarStarterKt"
    applicationName = "app"
}

configure<JavaPluginConvention> {//todo redundant?
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"//todo simplify in root build.gralde.kts
}

dependencies {
    implementation(project(":plugin-github-token"))
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$SERIALIZATION_VERSION")

    testImplementation("org.slf4j:slf4j-jdk14:1.7.25")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation(gradleTestKit())
}

tasks {
    getByName("processResources") {
        dependsOn(":client:webBuildProduction")
    }
}
sourceSets {
    main {
        resources {
            srcDirs("src/main/resources", "../client/build/distributions")
        }
    }
}
