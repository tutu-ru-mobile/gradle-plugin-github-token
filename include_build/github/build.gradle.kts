plugins {
    id("org.jetbrains.kotlin.multiplatform")
}
val COROUTINES_VERSION = "1.3.7"

kotlin {
    js {
        browser {

        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":network"))
                implementation(project(":lib"))
                implementation(kotlin("stdlib-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$COROUTINES_VERSION")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.getByName<org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile>("compileKotlinJs") {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xallow-result-return-type")
//        freeCompilerArgs += listOf("-Xir-produce-js", "-Xgenerate-dts")
    }
}
