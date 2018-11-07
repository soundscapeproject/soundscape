package com.example.dinhh.soundscape.presentation

import android.app.Application
import com.example.dinhh.soundscape.common.schedulerModule
import com.example.dinhh.soundscape.data.dataModule
import com.example.dinhh.soundscape.device.deviceModule
import com.example.dinhh.soundscape.domain.useCaseModule
import org.koin.android.ext.android.startKoin

class SoundscapeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            schedulerModule,
            useCaseModule,
            dataModule,
            presentationModule,
            deviceModule
        ))
    }
}