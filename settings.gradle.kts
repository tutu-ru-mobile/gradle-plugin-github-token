rootProject.name = "ru.tutu.github.token"
includeBuild("include_build") {
    dependencySubstitution {
        substitute(module("ru.tutu:plugin-github-token:SNAPSHOT")).with(project(":plugin-github-token"))
    }
}
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-plugin-github-token")
        }
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "ru.tutu.github.token" -> useModule("ru.tutu:plugin-github-token:SNAPSHOT")
            }
        }
    }
}

include("sample-kts")
include("sample-groovy")
