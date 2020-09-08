plugins {
    kotlin("jvm")
    application
}

val KTOR_VERSION = "1.3.2"

application {
    mainClassName = "MainKt"
    applicationName = "app"
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-server-netty:$KTOR_VERSION")
    implementation("io.ktor:ktor-server-cio:$KTOR_VERSION")
    implementation("io.ktor:ktor-client-core:$KTOR_VERSION")
    implementation("io.ktor:ktor-client-apache:$KTOR_VERSION")
    implementation("io.ktor:ktor-html-builder:$KTOR_VERSION")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
