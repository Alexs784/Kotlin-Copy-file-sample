package com.alessandro.copyfilesample.results

import java.io.File

sealed class FileResult {

    data class Loading(val value: Boolean) : FileResult()

    data class Created(val fileCreated: File) : FileResult()

    data class Copied(val fileCopied: File) : FileResult()

    object Error : FileResult()
}