package com.cmoney.backend2.common.extension

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Throws(NoSuchAlgorithmException::class)
internal fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    md.update(this.toByteArray())
    val digest = md.digest()
    val buffer = StringBuffer()
    val codingString = "%02x"
    for (b in digest) {
        buffer.append(String.format(codingString, b.toInt() and 0xff))
    }
    return buffer.toString()
}