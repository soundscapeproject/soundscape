package com.example.dinhh.soundscape.presentation

import android.app.Application
import com.example.dinhh.soundscape.common.schedulerModule
import com.example.dinhh.soundscape.data.pref.prefModule
import com.example.dinhh.soundscape.data.remote.remoteModule
import com.example.dinhh.soundscape.data.repository.repositoryModule
import com.example.dinhh.soundscape.domain.userUseCaseModule
import org.koin.android.ext.android.startKoin

class SoundscapeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            schedulerModule,
            userUseCaseModule,
            prefModule,
            remoteModule,
            repositoryModule,
            presentationModule
        ))
    }
}