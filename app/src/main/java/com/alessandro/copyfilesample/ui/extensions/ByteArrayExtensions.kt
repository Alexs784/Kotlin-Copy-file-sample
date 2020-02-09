package com.alessandro.copyfilesample.ui.extensions

fun ByteArray.printableHexString(): String {
    val hexString: StringBuilder = StringBuilder()
    for (messageDigest: Byte in this) {
        var h: String = Integer.toHexString(0xFF and messageDigest.toInt())
        while (h.length < 2)
            h = "0$h"
        hexString.append(h)
    }
    return hexString.toString()
}