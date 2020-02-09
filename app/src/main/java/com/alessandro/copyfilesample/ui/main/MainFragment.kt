package com.alessandro.copyfilesample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alessandro.copyfilesample.R
import com.alessandro.copyfilesample.results.FileResult
import com.alessandro.copyfilesample.ui.extensions.generateChecksum
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val fileViewModel by viewModel<FileViewModel>()
    private lateinit var originalFileChecksum: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFileResultLiveData()
        copySmallFileButton.setOnClickListener {
            fileViewModel.copyNewFileIntoNewOne(10, requireActivity().filesDir)
        }
        copyBigFileButton.setOnClickListener {
            fileViewModel.copyNewFileIntoNewOne(2200, requireActivity().filesDir)
        }
    }


    private fun observeFileResultLiveData() {
        fileViewModel.fileResultLiveData()
            .observe(viewLifecycleOwner, Observer<FileResult> { fileResult ->
                when (fileResult) {
                    is FileResult.Loading -> toggleLoadingState(fileResult.value)
                    is FileResult.Created -> displayCreatedFileInfo(fileResult.fileCreated)
                    is FileResult.Copied -> displayCopiedFileInfo(fileResult.fileCopied)
                }
            })
    }

    private fun toggleLoadingState(show: Boolean) {
        viewProgressLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun displayCreatedFileInfo(fileCreated: File) {
        originalFileChecksum = fileCreated.generateChecksum()
        val message = "File to copy created\n"
        infoTextView.text = message
    }

    private fun displayCopiedFileInfo(fileCopied: File) {
        var message = infoTextView.text.toString()
        message += if (originalFileChecksum == fileCopied.generateChecksum()) {
            "File copied properly, checksums are the same\n\nChecksum:\n$originalFileChecksum"
        } else {
            "File copied but something went wrong, checksums are not the same"
        }
        infoTextView.text = message
    }
}
