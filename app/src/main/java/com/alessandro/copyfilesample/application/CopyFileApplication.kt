package com.alessandro.copyfilesample.application

import android.app.Application
import com.alessandro.copyfilesample.koin.applicationModule
import com.alessandro.copyfilesample.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CopyFileApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CopyFileApplication)
            modules(listOf(applicationModule, viewModelModule))
        }
    }
}