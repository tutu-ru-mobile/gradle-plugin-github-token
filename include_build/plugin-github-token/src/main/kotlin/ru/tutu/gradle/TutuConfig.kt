package ru.tutu.gradle

import org.gradle.api.Project
import java.util.*

open class GitHubTokenExtension {
    var propertyPrefix: String = "github.packageToken"//todo rename

    /**
     * Save github token to ~/.gradle/gradle.properties
     */
    var homeDir: HomeDirConfig = HomeDirConfig()//todo rename homeDir

    /**
     * Scope of GitHub token. Use white space delimiter.
     * https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
     */
    var scope: String = "read:packages"

    /**
     * id to configure multiple github token's
     */
    var id: String = "default"

    class HomeDirConfig(
        /**
         * Token's saved on home dir must be encrypted. Specify AES secret.
         * If not present - token will be saved on local.properties (must be included in .gitignore)
         */
        var secretAES: String? = null
    )

    fun getToken(project: Project): String {
        val properties = Properties()
        val propertiesFile = project.rootProject.file("local.properties")
        if (propertiesFile.exists()) {
            properties.load(propertiesFile.inputStream())
            val property: String? = properties.getProperty(getPropertyKey())
            if (property != null) {
                val secretAES = homeDir.secretAES
                if(secretAES != null) {
                    return Aes.decrypt(secretAES.mask256bit, property)
                } else {
                    return property
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
        return propertyPrefix + id.capitalize()
    }

}
