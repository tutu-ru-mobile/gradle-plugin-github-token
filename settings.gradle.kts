pluginManagement {
    repositories {
//        mavenLocal()
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin")
//            credentials {
//                //https://github.com/settings/tokens
//                username = getVar("MAVEN_USER", "mavenUser")
//                password = getVar("MAVEN_PASSWORD", "mavenPassword")
//            }
        }
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

rootProject.name = "tutu-gradle-bootstrap"
