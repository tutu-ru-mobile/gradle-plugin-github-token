import java.util.Properties

plugins {
    kotlin("jvm") //version "1.3.72"
    `maven-publish`
}

repositories {
    jcenter()
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}


publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            //setUrl("https://maven.pkg.github.com/tutu-ru-mobile/android-core/")
            setUrl("https://maven.pkg.github.com/tutu-ru-mobile/gradle-bootstrap-plugin/")
            credentials {
                //https://github.com/settings/tokens
                username = getVar("MAVEN_USER", "github.user")//getVar("MAVEN_USER", "mavenUser")
                password = getVar("MAVEN_PASSWORD", "github.packageToken")//getVar("MAVEN_PASSWORD", "mavenPassword")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.tutu"
            artifactId = "lib1"
            version = "1.2"

            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
//    publications {
//        register("mavenJava", MavenPublication::class) {
//            from(components["java"])
//            artifact(sourcesJar.get())
//        }
//    }
}
//publishing {

//    publications {
//        register("gpr") {
//            from(components["java"])
//        }
//    }
//}
