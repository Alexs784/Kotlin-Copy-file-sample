package com.alessandro.copyfilesample.util

import java.io.File
import java.io.OutputStream
import java.util.*

class FileUtil {

    companion object {
        const val ONE_MEGABYTE = 1024 * 1024
    }

    fun copyFileDataByOutputStream(fileToCopy: File, destinationFileOutputStream: OutputStream) {
        val fileInputStream = fileToCopy.inputStream()
        try {
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytes = fileInputStream.read(buffer)
            while (bytes >= 0) {
                destinationFileOutputStream.write(buffer, 0, bytes)
                bytes = fileInputStream.read(buffer)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            destinationFileOutputStream.flush()
            destinationFileOutputStream.close()
            fileInputStream.close()
        }
    }

    fun createFileWithRandomData(
        sizeInMegabytes: Int,
        parentFolder: File = File("default")
    ): File {

        if (!parentFolder.exists())
            parentFolder.mkdirs()

        val file = File(parentFolder, "testFile + ${System.currentTimeMillis()}")
        file.createNewFile()
        file.fillWithRandomData(sizeInMegabytes)
        return file
    }

    private fun File.fillWithRandomData(sizeInMegabytes: Int) {
        if (sizeInMegabytes > 0) {
            val bytes = ByteArray(ONE_MEGABYTE)
            for (i in 0 until sizeInMegabytes) {
                Random().nextBytes(bytes)
                appendBytes(bytes)
            }
        }
    }
}