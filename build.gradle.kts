import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("ru.tutu:tutu_gradle:1.0.0")
    }

}

plugins {
    kotlin("jvm") version "1.3.70"
    `maven-publish`
    id("java-gradle-plugin")
    id("com.github.ben-manes.versions") version "0.28.0"
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
    id("com.gradle.plugin-publish") version "0.12.0"
//    id("ru.tutu.bootstrap") version "0.4.0"//todo sample usage
}

val PLUGIN_NAME = "bootstrap-plugin"
val PLUGIN_ID = "ru.tutu.bootstrap"
val VERSION = "0.4.0"
group = "ru.tutu"
version = VERSION

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// Use java-gradle-plugin to generate plugin descriptors and specify plugin ids
gradlePlugin {
    plugins {
        create(PLUGIN_NAME) {
            id = PLUGIN_ID
            implementationClass = "com.cherryperry.gfe.FileEncryptPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/tutu-ru-mobile/gradle-bootstrap-plugin"
    vcsUrl = "https://github.com/tutu-ru-mobile/gradle-bootstrap-plugin.git"
    description = "Tutu.ru gradle bootstrap-plugin"
    tags = listOf("configuration", "settings")//some string tags

//    plugins.create("gradleFileEncryptPlugin") {
//        id = "ru.tutu"
//        displayName = "Gradle unicorn plugin"
//    }
    (plugins) {
        PLUGIN_NAME {
            // id is captured from java-gradle-plugin configuration
            displayName = "tutu bootstrap-plugin"
            tags = listOf("configuration", "settings")
            version = VERSION
        }
    }
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.eclipse.jgit", "org.eclipse.jgit", "5.4.0.201906121030-r")
    implementation(kotlin("stdlib-jdk8", "1.3.70"))
    compileOnly(gradleApi())
    testImplementation("junit", "junit", "4.12")
    testImplementation(gradleTestKit())
    detektPlugins("io.gitlab.arturbosch.detekt", "detekt-formatting", "1.6.0")
}


publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
//    publications {
//        register("gpr") {
//            from(components["java"])
//        }
//    }
}
