package aes

import org.junit.Test
import kotlin.test.assertEquals

class EncryptTextTest {

    val key = byteArrayOf(
        0x2b, 0x7e, 0x15, 0x16,
        0x28, 0xae.toByte(), 0xd2.toByte(), 0xa6.toByte(),
        0xab.toByte(), 0xf7.toByte(), 0x15, 0x88.toByte(),
        0x09, 0xcf.toByte(), 0x4f, 0x3c
    )

    @Test
    fun testEncryptDecrypt16Bytes() {
        val aes = Aes()
        val plainText = "1234567890123456"
        val plainBytes = plainText.toByteArray()
        val cipher = aes.encrypt(plainBytes, key)
        val decryptBytes = aes.decrypt(cipher, key)
        val decryptText = String(decryptBytes)
        assertEquals(plainText, decryptText)
    }

    @Test
    fun testEncryptDecrypt5Bytes() {
        val aes = Aes()
        val plainText = "12345"
        val plainBytes = plainText.toByteArray()
        val cipher = aes.encrypt(plainBytes, key)
        val decryptBytes = aes.decrypt(cipher, key)
        val decryptText = String(decryptBytes)
        assertEquals(plainText, decryptText)
    }

    @Test
    fun testEncryptDecrypt20Bytes() {
        val aes = Aes()
        val plainText = "12345678901234567890"
        val plainBytes = plainText.toByteArray()
        val cipher = aes.encrypt(plainBytes, key)
        val decryptBytes = aes.decrypt(cipher, key)
        val decryptText = String(decryptBytes)
        assertEquals(plainText, decryptText)
    }

}

