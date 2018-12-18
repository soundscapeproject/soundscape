package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Single

class PlaySingleSoundScapeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Plays a single sound in the soundscape.
     **/
    fun execute(index: Int): Single<Int> {
        return soundScape.playSingleSoundScapes(index)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}