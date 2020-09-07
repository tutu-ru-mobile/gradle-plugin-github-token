package ru.tutu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.sample.startSimpleServer
import ru.tutu.log.PrintLogs
import ru.tutu.log.TutuLog

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
            project.tasks.create("myTask1").doFirst {
                TutuLog.warning(it.info)
                startSimpleServer(8081)
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

val Task.info get() = "task $name"

