plugins {
    kotlin("jvm")
    application//todo not need application
}

application {
    mainClassName = "MainKt"//todo rename file Main
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
