package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class StopSoundsScapeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Stops all the sounds in the soundscape.
     **/
    fun execute(): Completable {
        return soundScape.stopSoundScapes()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}