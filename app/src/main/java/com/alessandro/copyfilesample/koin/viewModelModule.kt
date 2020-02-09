package com.alessandro.copyfilesample.koin

import com.alessandro.copyfilesample.ui.main.FileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FileViewModel(get()) }
}