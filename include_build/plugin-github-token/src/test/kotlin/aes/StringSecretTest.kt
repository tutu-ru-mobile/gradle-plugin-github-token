package aes

import org.junit.Test
import kotlin.test.assertEquals

class StringSecretTest {
    @Test
    fun testBytesSize1() {
        val bytes =
            AesStringSecret("a")
                .toByteArray16()
        assertEquals(16, bytes.size)
    }

    @Test
    fun testBytesSize2() {
        val bytes =
            AesStringSecret("awertyuiosdfghjkxcvbnmxcvbnrghxcvb")
                .toByteArray16()
        assertEquals(16, bytes.size)
    }
}
