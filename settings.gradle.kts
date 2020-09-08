
rootProject.name = "tutu-gradle-bootstrap"
includeBuild("include_build") {
    dependencySubstitution {
        substitute(module("ru.tutu:tutu_gradle:1.0.0")).with(project(":tutu_gradle"))
        //substitute(module("com.github.raniejade:godot-kotlin-gradle-plugin:0.1.0")).with(project(":godot-kotlin-gradle-plugin"))
    }
}
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin")
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
//                "tutu-gradle" -> useModule("ru.tutu:tutu_gradle:1.0.0")
                "ru-tutu-github-package" -> useModule("ru.tutu:tutu_gradle:1.0.0")
////        "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
////        "org.jetbrains.kotlin.multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${target.version}")
            }
        }
    }
}

include("sample")
