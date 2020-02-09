package com.alessandro.copyfilesample.ui.extensions

import java.io.File
import java.security.MessageDigest

fun File.generateChecksum(): String {
    val inputStream = inputStream()
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var bytes = inputStream.read(buffer)
    val digest: MessageDigest = MessageDigest.getInstance("SHA-512")

    while (bytes >= 0) {
        digest.digest(buffer, 0, bytes)
        bytes = inputStream.read(buffer)
    }

    return buffer.printableHexString()
}