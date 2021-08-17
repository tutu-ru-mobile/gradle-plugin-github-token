package ru.tutu.token

import getGithubToken
import ru.tutu.gradle.AesWrapper
import ru.tutu.log.TutuLog
import ru.tutu.token.config.*
import java.io.File

fun main(args:Array<String>) {
  val fileName = args.firstOrNull() ?: "create-token-cli.json"
  println("create token")
  println("read file $fileName:")
  val configText = File(fileName).readText()
  println(configText)

  val config: SerializableConfig = configText.toSerializableConfig()
  println("parsed config:")
  println(config)
  println("Now let's create token:")
  val token = getGithubToken(config.scope, false)
  val secretAES = config.secretAES
  val propertiesFile: File = config.tokenLocation.getPropertiesFile(File("."))
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
}
