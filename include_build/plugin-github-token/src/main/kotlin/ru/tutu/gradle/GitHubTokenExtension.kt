package ru.tutu.gradle

import org.gradle.api.Project
import ru.tutu.log.TutuLog
import java.io.File
import java.util.*

open class GitHubTokenExtension {
    /**
     * Scope of GitHub token. Use white space delimiter.
     * Also look at:
     * https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
     */
    var scope: String = "read:packages"

    /**
     * Location of *.properties file to save github token
     */
    internal var tokenLocation: TokenLocation = TokenLocation.LocalProperties

    /**
     * Save token at local.properties
     * Attention! Please add local.properties to .gitignore
     */
    fun storeTokenAtLocalProperties() {
        tokenLocation = TokenLocation.LocalProperties
    }

    /**
     * Save github token at ~/.gradle/gradle.properties
     * Attention! Please use AES key to encrypt this globally available token!
     */
    fun storeTokenAtHomeGradleProperties() {
        tokenLocation = TokenLocation.HomeGradleProperties
    }

    /**
     * You may specify custom *.properties file location
     */
    fun storeTokenAtCustomLocation(file: File) {
        tokenLocation = TokenLocation.CustomLocation(file)
    }

    /**
     * AES secret key (256-bit maximum size)
     */
    var secretAES: String? = null

    /**
     * Prefix in properties file
     */
    var propertyPrefix: String = "github.token"

    /**
     * id to configure multiple github token's in different projects
     */
    var id: String = "default"

    fun getToken(project: Project): String {
        val propertiesFile = tokenLocation.getPropertiesFile(project)
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

internal sealed class TokenLocation {
    /**
     * Save github token to local.properties
     * Attention! Please add local.properties to .gitignore
     */
    object LocalProperties : TokenLocation()

    /**
     * Save github token to ~/.gradle/gradle.properties
     * Attention! Please use AES key to encrypt this globally available token!
     */
    object HomeGradleProperties : TokenLocation()

    /**
     * You may specify custom *.properties file location
     */
    class CustomLocation(val file: File) : TokenLocation()
}

internal fun TokenLocation.getPropertiesFile(project: Project): File =
    when (this) {
        is TokenLocation.LocalProperties -> {
            project.localProperties()
        }
        is TokenLocation.HomeGradleProperties -> {
            userHomeGradleProperties()
        }
        is TokenLocation.CustomLocation -> {
            this.file
        }
    }

internal fun userHomeGradleProperties(): File =
    File(System.getProperty("user.home"))
        .resolve(".gradle")
        .resolve("gradle.properties")

internal fun Project.localProperties(): File =
    rootProject.file("local.properties")
