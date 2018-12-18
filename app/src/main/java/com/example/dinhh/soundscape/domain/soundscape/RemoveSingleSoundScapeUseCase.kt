package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class RemoveSingleSoundScapeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Removes the selected sound from the soundscape.
     **/
    fun execute(index: Int): Completable {
        return soundScape.removeSingleSoundScapes(index)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}