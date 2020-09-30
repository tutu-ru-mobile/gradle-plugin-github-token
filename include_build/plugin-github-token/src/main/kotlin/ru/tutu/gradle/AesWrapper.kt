package ru.tutu.gradle

import aes.Aes
import aes.AesStringSecret
import com.soywiz.krypto.encoding.base64
import com.soywiz.krypto.encoding.fromBase64

object AesWrapper {

    val aes = Aes()

    fun encrypt(strToEncrypt: String, secretKey: String): String {
        val encrypted = aes.encrypt(strToEncrypt.toByteArray(), AesStringSecret(secretKey))
        return encrypted.base64
    }

    fun decrypt(secretKey: String, strToDecrypt: String): String {
        try {
            val decrypt = aes.decrypt(strToDecrypt.fromBase64(), AesStringSecret(secretKey))
            return String(decrypt)
        } catch (t: Throwable) {
            return "decrypt fail with exception"
        }
    }

}
