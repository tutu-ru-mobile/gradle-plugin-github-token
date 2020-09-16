package ru.tutu.gradle

import com.soywiz.krypto.AES
import ru.tutu.log.TutuLog

object Aes {
    fun encrypt(strToEncrypt: String, secret_key: String): String {
        try {
            return ChCrypto.aesEncrypt(strToEncrypt, secret_key)
        } catch (t: Throwable) {
            t.printStackTrace()
            TutuLog.error("encrypt fail 2")
            return "error2"
        }
    }

    fun decrypt(key: String, strToDecrypt: String): String {
        try {
            return ChCrypto.aesDecrypt(strToDecrypt, key)
        } catch (t: Throwable) {
            t.printStackTrace()
            TutuLog.error("decrypt fail 2")
            return "error2"
        }
    }
}

val String.mask256bit: String
    get():String =
        (this + List(32) { "0" }.joinToString(""))
            .substring(0..31)
