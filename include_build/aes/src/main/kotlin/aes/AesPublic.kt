package aes

import util.byteArray4toInt
import util.join
import util.splitBy16ByteBlock
import util.toByteArray4

fun Aes.encrypt(plain: ByteArray, key: ByteArray) =
    encrypt(plain, AesRawBytesSecret(key))

fun Aes.decrypt(cipher: ByteArray, key: ByteArray) =
    decrypt(cipher, AesRawBytesSecret(key))

fun Aes.encrypt(plain: ByteArray, key: AesSecret): ByteArray {
    val keyBytes: ByteArray = key.toByteArray16()
    val size = plain.size
    val sizeBytes: ByteArray = size.toByteArray4()
    val sizeAndPlain: ByteArray = sizeBytes + plain
    val plainBlocks: List<ByteArray> = sizeAndPlain.splitBy16ByteBlock()
    val encryptedBlocks = plainBlocks.map { encrypt16Bytes(it, keyBytes) }
    val cipher = encryptedBlocks.join()
    return cipher
}

fun Aes.decrypt(cipher: ByteArray, key: AesSecret): ByteArray {
    val keyBytes: ByteArray = key.toByteArray16()
    val encryptedBlocks = cipher.splitBy16ByteBlock()
    val plainBlocks: List<ByteArray> = encryptedBlocks.map { decrypt16Bytes(it, keyBytes) }
    val sizeAndPlainWithTail: ByteArray = plainBlocks.join()
    val sizeBytes: ByteArray = sizeAndPlainWithTail.sliceArray(0..3)
    val size = sizeBytes.byteArray4toInt()
    val plain = sizeAndPlainWithTail.sliceArray(4 until (4 + size))
    return plain
}
