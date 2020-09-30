package aes

import com.soywiz.krypto.Hash
import com.soywiz.krypto.MD5

interface AesSecret {
    fun toByteArray16(): ByteArray
}

class AesRawBytesSecret(val secret: ByteArray) : AesSecret {
    override fun toByteArray16(): ByteArray = secret
}

class AesStringSecret(val secret: String) : AesSecret {
    override fun toByteArray16(): ByteArray {
        val digest: Hash = MD5.digest(secret.toByteArray())
        return digest.bytes
    }
}
