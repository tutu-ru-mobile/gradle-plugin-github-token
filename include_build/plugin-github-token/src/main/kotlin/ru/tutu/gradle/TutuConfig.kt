package ru.tutu.gradle

import org.gradle.api.Project
import ru.tutu.log.TutuLog
import java.io.File
import java.util.*

open class GitHubTokenExtension {
    var propertyPrefix: String = "github.token"

    /**
     * Save github token to ~/.gradle/gradle.properties
     */
    var saveToHomeDir: Boolean = false

    var secretAES: String? = null

    /**
     * Scope of GitHub token. Use white space delimiter.
     * https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
     */
    var scope: String = "read:packages"

    /**
     * id to configure multiple github token's
     */
    var id: String = "default"

    fun getToken(project: Project): String {
        val propertiesFile = if (saveToHomeDir) userHomeGradleProperties() else project.localProperties()
        val properties = Properties()
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
            val property: String? = properties.getProperty(getPropertyKey())
            if (property != null) {
                val secretAES = secretAES
                return if (secretAES != null) {
                    Aes.decrypt(secretAES.mask256bit, property)
                } else {
                    property
                }
            } else {
                TutuLog.error("property key ${getPropertyKey()} not exists")
                return "error"
            }
        } else {
            TutuLog.error("properties file (${propertiesFile.path}) not exists")
            return "error"
        }
    }

    fun getPropertyKey(): String {
        val postfix: String = if (secretAES != null) {
            "Base64AES"
        } else {
            ""
        }
        return propertyPrefix + id.capitalize() + postfix
    }

}

fun userHomeGradleProperties():File =
    File(System.getProperty("user.home"))
        .resolve(".gradle")
        .resolve("gradle.properties")

fun Project.localProperties():File =
    rootProject.file("local.properties")
