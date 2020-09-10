package ru.tutu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.tutu.log.PrintLogs
import ru.tutu.log.TutuLog
import runStaticWebServer

class TutuGradlePlugin : Plugin<Project> {

    lateinit var config: TutuConfig

    init {
        TutuLog.addPlugin(PrintLogs())
    }

    override fun apply(project: Project) {
        saveExecute("configure tutu-gradle plugin") {
            config = project.extensions.create("tutu", TutuConfig::class.java)
            project.afterEvaluate {
                afterEvaluate(project)
            }
        }
    }

    fun afterEvaluate(project: Project) {
        saveExecute("configureTutuTasks") {
            project.tasks.create("my").doFirst {task ->
                TutuLog.warning("task: ${task.name}")
                runStaticWebServer(config.scope) {token ->
                    val file = project.rootProject.rootDir.resolve("local.properties")
                    val str = file.readText()
                    file.writeText(str + "\n${config.getPropertyKey()}=${token}")
                    TutuLog.info("done, github token saved")
                    System.exit(0)
                }
            }
        }
    }

    fun saveExecute(info: String, block: () -> Unit) {
        try {
            block()
        } catch (t: Throwable) {
            TutuLog.error("Exception occurs $info: ${t.message}")
            throw t
        }
    }

}
