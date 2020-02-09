package com.alessandro.copyfilesample.util

import com.alessandro.copyfilesample.ui.extensions.generateChecksum
import com.alessandro.copyfilesample.util.FileUtil.Companion.ONE_MEGABYTE
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class FileUtilTest {

    private lateinit var fileCopied: File

    private lateinit var fileUtil: FileUtil

    @Before
    fun setup() {
        fileCopied = File("fileCopiedTestPath")

        fileUtil = FileUtil()
    }

    @Test
    fun shouldCreateFileWithRandomDataAndGivenSize() {
        val delta = 0.0
        val megabytes = 2
        val fileCreated = fileUtil.createFileWithRandomData(megabytes)
        fileCreated.deleteOnExit()

        val expectedSize = megabytes.toDouble() * ONE_MEGABYTE
        assertEquals(expectedSize, fileCreated.length().toDouble(), delta)
    }

    @Test
    fun shouldCopySmallFileBytesIntoNewFile() {
        val delta = 0.0
        val megabytes = 2
        val fileToCopy = fileUtil.createFileWithRandomData(megabytes)
        fileToCopy.deleteOnExit()

        val expectedSize = megabytes.toDouble() * ONE_MEGABYTE
        assertEquals(expectedSize, fileToCopy.length().toDouble(), delta)

        fileUtil.copyFileDataByOutputStream(fileToCopy, fileCopied.outputStream())

        assertEquals(fileCopied.generateChecksum(), fileToCopy.generateChecksum())
    }

    @Test
    fun shouldCopyBigFileBytesIntoNewFile() {
        val delta = 0.0
        val megabytes = 3000
        val fileToCopy = fileUtil.createFileWithRandomData(megabytes)
        fileToCopy.deleteOnExit()

        val expectedSize = megabytes.toDouble() * ONE_MEGABYTE
        assertEquals(expectedSize, fileToCopy.length().toDouble(), delta)

        fileUtil.copyFileDataByOutputStream(fileToCopy, fileCopied.outputStream())

        assertEquals(fileCopied.generateChecksum(), fileToCopy.generateChecksum())
    }

    @After
    fun teardown() {
        fileCopied.deleteRecursively()
    }
}