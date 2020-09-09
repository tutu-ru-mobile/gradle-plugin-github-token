import java.util.Properties

plugins {
    kotlin("jvm")
    `maven-publish`
}

fun getVar(env:String, key: String): String {
    val envVar:String? = System.getenv(env)
    if(envVar != null) {
        return envVar
    }
    val properties = Properties()
    val propertiesFile = project.rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
        val property: String? = properties.getProperty(key)
        if (property != null) {
            return property
        } else {
            return "todo"//todo
            throw Error("property $key not exists")
        }
    } else {
        throw Error("local.properties not exists")
    }
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
            version = "1.1"

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
