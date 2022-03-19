package com.example.capstone

import android.app.Application
import com.example.capstone.di.useCaseModule
import com.example.capstone.di.viewModelModule
import com.example.core.di.databaseModule
import com.example.core.di.networkModule
import com.example.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    databaseModule,
                    networkModule,
                )
            )
        }
    }
}