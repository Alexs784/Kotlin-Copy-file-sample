package com.alessandro.copyfilesample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alessandro.copyfilesample.results.FileResult
import com.alessandro.copyfilesample.util.FileUtil
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class FileViewModel(private val fileUtil: FileUtil) : ViewModel(), CoroutineScope {

    private val viewModelJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    private val fileResultLiveData = MutableLiveData<FileResult>()

    fun fileResultLiveData(): LiveData<FileResult> = fileResultLiveData

    fun copyNewFileIntoNewOne(newFileSizeInMegabytes: Int, parentFolder: File) {
        launch {
            try {
                fileResultLiveData.value = FileResult.Loading(true)

                val originFile = withContext(Dispatchers.IO) {
                    fileUtil.createFileWithRandomData(newFileSizeInMegabytes, parentFolder)
                }

                fileResultLiveData.value = FileResult.Created(originFile)

                val destinationFile = withContext(Dispatchers.IO) {
                    val destinationFile = fileUtil.createFileWithRandomData(0, parentFolder)
                    fileUtil.copyFileDataByOutputStream(originFile, destinationFile.outputStream())
                    destinationFile
                }
                fileResultLiveData.value = FileResult.Copied(destinationFile)

            } catch (exception: Exception) {
                fileResultLiveData.value = FileResult.Error
            } finally {
                fileResultLiveData.value = FileResult.Loading(false)
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
