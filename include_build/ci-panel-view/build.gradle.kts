plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    js {
        useCommonJs()
        browser {
            testTask {
                testLogging {
                    showExceptions = true
//                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                    showCauses = true
                    showStackTraces = true
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION")
                implementation(project(":lib"))
                implementation(project(":lib-mvi"))
                implementation(project(":github"))
                implementation(project(":network"))
                implementation(project(":ci-panel-mvi"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$COROUTINES_VERSION")
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
                implementation("org.jetbrains:kotlin-react:$REACT_KT_V-$KT_WRAPPER_V")
                implementation("org.jetbrains:kotlin-react-dom:$REACT_KT_V-$KT_WRAPPER_V")
                implementation("org.jetbrains:kotlin-styled:1.0.0-$KT_WRAPPER_V")
                implementation("org.jetbrains:kotlin-extensions:1.0.1-$KT_WRAPPER_V")
                implementation("org.jetbrains:kotlin-css-js:1.0.0-$KT_WRAPPER_V")
                implementation(npm("core-js", "2.6.5"))
                implementation(npm("react", "16.13.0"))
                implementation(npm("react-dom", "16.13.0"))
                implementation(npm("react-is", "16.13.0"))
                implementation(npm("inline-style-prefixer", "5.1.0"))
                implementation(npm("styled-components", "4.3.2"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
