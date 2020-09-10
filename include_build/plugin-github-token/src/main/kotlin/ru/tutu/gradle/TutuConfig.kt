package ru.tutu.gradle

import org.gradle.api.Project
import java.util.*

open class GitHubTokenExtension {
    var propertyPrefix: String = "github.packageToken"//todo rename

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
        val properties = Properties()
        val propertiesFile = project.rootProject.file("local.properties")
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
                return "todo"//todo
                throw Error("property ${getPropertyKey()} not exists")
            }
        } else {
//        return "todo_error"//todo
            throw Error("local.properties not exists")
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
