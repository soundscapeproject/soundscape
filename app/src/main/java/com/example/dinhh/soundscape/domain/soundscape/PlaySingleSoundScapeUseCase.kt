package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class PlaySingleSoundScapeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    fun execute(index: Int): Completable {
        return soundScape.playSingleSoundScapes(index)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}