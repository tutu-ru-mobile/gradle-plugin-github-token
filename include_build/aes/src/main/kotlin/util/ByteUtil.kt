package util

/**
 * Output ByteArray with size == 4
 */
fun Int.toByteArray4(): ByteArray =
    byteArrayOf(
        (this ushr 0).toByte(),
        (this ushr 8).toByte(),
        (this ushr 16).toByte(),
        (this ushr 24).toByte()
    )

/**
 * Input ByteArray with size == 4
 */
fun ByteArray.byteArray4toInt(): Int =
    (get(0).toInt() shl 0) +
            (get(1).toInt() shl 8) +
            (get(2).toInt() shl 16) +
            (get(3).toInt() shl 24)

fun ByteArray.splitBy16ByteBlock(): List<ByteArray> {
    return sequence {
        for (i in 0 until size step 16) {
            val beginBlock = i
            val endBlockExclusive = kotlin.math.min((i + 16), size)
            var result = copyOfRange(beginBlock, endBlockExclusive)
            if (result.size < 16) {
                result += ByteArray(16 - result.size)
            }
            yield(result)
        }
    }.toList()

//    this.sliceArray(0..15)
//    toList().windowed(16, 16, true)
}

fun List<ByteArray>.join() = reduce { acc, next -> acc + next }
