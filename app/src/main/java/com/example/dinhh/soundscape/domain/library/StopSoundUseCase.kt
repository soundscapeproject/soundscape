package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.StopSound
import io.reactivex.Completable

class StopSoundUseCase (private val stopSound: StopSound, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Completable {
        return stopSound.stopSound()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}