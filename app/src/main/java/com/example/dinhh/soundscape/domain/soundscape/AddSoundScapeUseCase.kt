package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import com.example.dinhh.soundscape.device.SoundscapeItem
import io.reactivex.Completable

class AddSoundScapeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Adds a new sound to a soundscape.
     **/
    fun execute(soundscapeItem: SoundscapeItem): Completable {
        return soundScape.addSound(soundscapeItem)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}