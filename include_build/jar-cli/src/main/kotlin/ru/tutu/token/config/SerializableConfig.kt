package ru.tutu.token.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.tutu.gradle.userHomeGradleProperties
import java.io.File

val jsonSerialization:Json = Json {
    this.prettyPrint = true
    this.ignoreUnknownKeys = true
    this.encodeDefaults = true
}

fun SerializableConfig.toJsonStr():String =
    jsonSerialization.encodeToString(SerializableConfig.serializer(), this)

fun String.toSerializableConfig():SerializableConfig =
    jsonSerialization.decodeFromString(SerializableConfig.serializer(), this)

@Serializable
data class SerializableConfig(
    val scope: String = "read:packages",
    val secretAES: String? = null,
    val tokenLocation: SerializableTokenLocation = SerializableTokenLocation.LocalProperties,
    val id: String = "default",
    val propertyPrefix: String = "github.token"
)

@Serializable
sealed class SerializableTokenLocation {
    @Serializable
    object LocalProperties : SerializableTokenLocation()
    @Serializable
    object HomeGradleProperties : SerializableTokenLocation()
    @Serializable
    data class CustomLocation(val filePath: String) : SerializableTokenLocation()
}

fun SerializableTokenLocation.getPropertiesFile(workingDir: File): File =
    when (this) {
        is SerializableTokenLocation.LocalProperties -> {
            workingDir.resolve("local.properties")
        }
        is SerializableTokenLocation.HomeGradleProperties -> {
            userHomeGradleProperties()
        }
        is SerializableTokenLocation.CustomLocation -> {
            File(this.filePath)
        }
    }

fun SerializableConfig.getPropertyKey(): String {
    val postfix: String = if (secretAES != null) {
        "Base64AES"
    } else {
        ""
    }
    return propertyPrefix + id.capitalize() + postfix
}
