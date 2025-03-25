package com.inimitable.ideaplatformtest.core

import android.app.Application
import com.inimitable.ideaplatformtest.core.di.dataBaseModule
import com.inimitable.ideaplatformtest.core.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(dataBaseModule, dataModule)
        }
    }
}