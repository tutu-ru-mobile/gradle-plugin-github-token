package aes

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AesStringSecretTest {
    @Test
    fun testEmptyString() {
        val bytesSecret = AesStringSecret("")
            .toByteArray16()
        assertEquals(16, bytesSecret.size)
    }

    @Test
    fun testShortString() {
        val bytesSecret = AesStringSecret("a")
            .toByteArray16()
        assertEquals(16, bytesSecret.size)
    }

    @Test
    fun testLongString() {
        val bytesSecret = AesStringSecret("aqwertyuiopasdfghjklzxcvbnm")
            .toByteArray16()
        assertEquals(16, bytesSecret.size)
    }

    @Test
    fun testDifferentKeys() {
        val bytesSecret1 = AesStringSecret("a")
            .toByteArray16()
        val bytesSecret2 = AesStringSecret("b")
            .toByteArray16()

        assertFalse(bytesSecret1.equalsBytes(bytesSecret2))
    }

    @Test
    fun testSameKeys() {
        val bytesSecret1 = AesStringSecret("a")
            .toByteArray16()
        val bytesSecret2 = AesStringSecret("a")
            .toByteArray16()

        assertTrue(bytesSecret1.equalsBytes(bytesSecret2))
    }

}

/**
 * Check equality of each Byte in ByteArray
 */
fun ByteArray.equalsBytes(other: ByteArray): Boolean {
    if (size != other.size) {
        return false
    }
    for (i in 0 until size) {
        if (get(i) != other[i]) {
            return false
        }
    }
    return true
}
