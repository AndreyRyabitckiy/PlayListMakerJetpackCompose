package com.example.playlistmakerjetpackcompose.root

import android.app.Application
import com.example.playlistmakerjetpackcompose.di.dataModule
import com.example.playlistmakerjetpackcompose.di.interatorModule
import com.example.playlistmakerjetpackcompose.di.repositoryModule
import com.example.playlistmakerjetpackcompose.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    interatorModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}