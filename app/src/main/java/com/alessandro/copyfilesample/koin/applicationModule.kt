package com.alessandro.copyfilesample.koin

import com.alessandro.copyfilesample.util.FileUtil
import org.koin.dsl.module

val applicationModule = module {
    single { FileUtil() }
}