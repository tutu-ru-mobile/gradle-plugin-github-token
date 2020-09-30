import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.12.0"
}

val PLUGIN_NAME = "GitHub token plugin"
val PLUGIN_ID = "ru.tutu.github.token"
val VERSION = "1.0.4"
val TAGS = listOf("github", "token")

group = "ru.tutu"
version = VERSION

repositories {
    jcenter()
//    maven { setUrl("https://dl.bintray.com/kotlin/ktor") }//todo remove?
}

configure<JavaPluginConvention> {//todo redundant?
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"//todo simplify in root build.gralde.kts
}

gradlePlugin {
    plugins {
        create(PLUGIN_ID) {
            id = PLUGIN_ID
            implementationClass = "ru.tutu.gradle.PluginGitHubToken"
        }
    }
}

pluginBundle {
    website = "https://github.com/tutu-ru-mobile/gradle-plugin-github-token"
    vcsUrl = "https://github.com/tutu-ru-mobile/gradle-plugin-github-token.git"
    description = "Plugin to configure GitHub token in gradle scripts."
    tags = TAGS

//    plugins.create("gradleFileEncryptPlugin") {
//        id = "ru.tutu"
//        displayName = "Gradle unicorn plugin"
//    }
    (plugins) {
        PLUGIN_ID {
            // id is captured from java-gradle-plugin configuration
            displayName = PLUGIN_NAME
            tags = TAGS
            version = VERSION
        }
    }
}

dependencies {
    compileOnly(gradleApi())

    implementation(project(":aes"))
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-server-netty:$KTOR_VERSION")
    implementation("io.ktor:ktor-server-cio:$KTOR_VERSION")
    implementation("io.ktor:ktor-client-core:$KTOR_VERSION")
    implementation("io.ktor:ktor-client-apache:$KTOR_VERSION")
    implementation("io.ktor:ktor-html-builder:$KTOR_VERSION")

    implementation("org.eclipse.jgit", "org.eclipse.jgit", "5.4.0.201906121030-r")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    testImplementation("org.slf4j:slf4j-jdk14:1.7.25")
    testImplementation("junit:junit:4.12")
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
