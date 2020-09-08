plugins {
    id("org.jetbrains.kotlin.js")
}

val KT_WRAPPER_V = "pre.102-kotlin-1.3.72"
val REACT_KT_V = "16.13.1"
val COROUTINES_VERSION = "1.3.7"

kotlin {
    target {
        useCommonJs()
        browser() {
            webpackTask {
                sourceMaps = false
            }
        }
    }
}

dependencies {
    implementation(project(":ci-panel-view"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$COROUTINES_VERSION")
    implementation(npm("core-js", "2.6.5"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation("org.jetbrains:kotlin-react:$REACT_KT_V-$KT_WRAPPER_V")
    implementation("org.jetbrains:kotlin-react-dom:$REACT_KT_V-$KT_WRAPPER_V")
    implementation("org.jetbrains:kotlin-styled:1.0.0-$KT_WRAPPER_V")
    implementation("org.jetbrains:kotlin-extensions:1.0.1-$KT_WRAPPER_V")
    implementation("org.jetbrains:kotlin-css-js:1.0.0-$KT_WRAPPER_V")
    implementation(npm("react", "16.13.0"))
    implementation(npm("react-dom", "16.13.0"))
    implementation(npm("react-is", "16.13.0"))
    implementation(npm("inline-style-prefixer", "5.1.0"))
    implementation(npm("styled-components", "4.3.2"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
    testImplementation(npm("enzyme", "3.9.0"))
    testImplementation(npm("enzyme-adapter-react-16", "1.12.1"))
}

tasks {
    register("webBuildProduction") {
        dependsOn("browserProductionWebpack")
    }
    register("myRun") {
        //todo delete
        doLast {
            SimpleHttpFileServerFactory().start(file("build/distributions/"), 8888)
            println("server started:")
            println("http://localhost:8888/index.html")
            Thread.sleep(Long.MAX_VALUE)
        }
    }
}
