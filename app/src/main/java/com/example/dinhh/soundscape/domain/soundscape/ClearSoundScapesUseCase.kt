package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class ClearSoundScapesUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Completable {
        return soundScape.clearSoundScapes()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}