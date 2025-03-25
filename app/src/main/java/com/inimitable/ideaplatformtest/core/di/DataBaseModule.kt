package com.inimitable.ideaplatformtest.core.di

import android.app.Application
import androidx.room.Room
import com.inimitable.ideaplatformtest.data.local.AppDatabase
import com.inimitable.ideaplatformtest.data.local.ProductsDao
import org.koin.dsl.module

fun provideDataBase(application: Application): AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "table_post"
    )
        .createFromAsset("data.db")
        .fallbackToDestructiveMigration(true)
        .build()

fun provideDao(postDataBase: AppDatabase): ProductsDao = postDataBase.productsDao()


val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}