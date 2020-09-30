package util

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteUtilTest {

    @Test
    fun testIntToByteArray() {
        val input = 2345
        val ba = input.toByteArray4()
        val output = ba.byteArray4toInt()
        assertEquals(input, output)
    }

    @Test
    fun testSplitBy16ByteBlockOne() {
        val byteBlocks = "a".toByteArray().splitBy16ByteBlock()
        assertEquals(1, byteBlocks.size)
        assertEquals(16, byteBlocks.last().size)
    }

    @Test
    fun testSplitBy16ByteBlockBig() {
        val byteBlocks = "aqwertyuiopasdfghjklzxcvbnmwertyu".toByteArray().splitBy16ByteBlock()
        assertTrue(byteBlocks.size > 1)
        assertEquals(16, byteBlocks.last().size)
    }

    @Test
    fun testSplitOneBlock() {
        val byteBlocks = "1234567890123456".toByteArray().splitBy16ByteBlock()
        assertEquals(1, byteBlocks.size)
        assertEquals(16, byteBlocks.last().size)
    }

}
