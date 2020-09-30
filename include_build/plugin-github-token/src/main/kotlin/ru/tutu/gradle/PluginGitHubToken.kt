package ru.tutu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.tutu.log.TutuLog
import runStaticWebServer

class PluginGitHubToken : Plugin<Project> {

    lateinit var config: GitHubTokenExtension

    override fun apply(project: Project) {
        saveExecute("configure tutu-gradle plugin") {
            config = project.extensions.create("gitHubToken", GitHubTokenExtension::class.java)
            project.afterEvaluate {
                afterEvaluate(project)
            }
        }
    }

    fun afterEvaluate(project: Project) {
        saveExecute("configureTutuTasks") {
            project.tasks.create("createToken").doFirst {task ->
                TutuLog.warning("task: ${task.name}")
                runStaticWebServer(config.scope) {token ->
                    val secretAES = config.secretAES
                    val propertiesFile = config.tokenLocation.getPropertiesFile(project)
                    val textFileContent: String =
                        if (propertiesFile.exists()) {
                            propertiesFile.readText()
                        } else {
                            ""
                        }
                    val writeTokenStr: String =
                        if (secretAES != null) {
                            AesWrapper.encrypt(token, secretAES)
                        } else {
                            token
                        }

                    if(!propertiesFile.parentFile.exists()) {
                        propertiesFile.parentFile.mkdirs()
                    }
                    propertiesFile.writeText(
                        textFileContent + "\n" +
                                "${config.getPropertyKey()}=$writeTokenStr" +
                                "\n"
                    )
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
