
rootProject.name = "ru.tutu.github.token"
includeBuild("include_build") {
    dependencySubstitution {
        substitute(module("ru.tutu:plugin-github-token:SNAPSHOT")).with(project(":plugin-github-token"))
        //substitute(module("com.github.raniejade:godot-kotlin-gradle-plugin:0.1.0")).with(project(":godot-kotlin-gradle-plugin"))
    }
}
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-plugin-github-token")
//            credentials {
////                //https://github.com/settings/tokens
//                username = ""//getVar("MAVEN_USER", "mavenUser")
//                password = ""//getVar("MAVEN_PASSWORD", "mavenPassword")
//            }
        }
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "ru.tutu.github.token" -> useModule("ru.tutu:plugin-github-token:SNAPSHOT")
////        "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
////        "org.jetbrains.kotlin.multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${target.version}")
            }
        }
    }
}

include("sample-kts")
include("sample-groovy")
