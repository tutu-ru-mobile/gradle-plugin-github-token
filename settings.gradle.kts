pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()//todo alternative?: maven { setUrl("https://plugins.gradle.org/m2/") }
    }

//  resolutionStrategy {
//    eachPlugin {
//      when (requested.id.id) {
////        "kotlin-dce-js" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
////        "kotlinx-serialization" -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
////        "org.jetbrains.kotlin.multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${target.version}")
//      }
//    }
//  }
}

rootProject.name = "gradle-file-encrypt"//todo
