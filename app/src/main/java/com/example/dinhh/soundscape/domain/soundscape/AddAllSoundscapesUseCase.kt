package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import com.example.dinhh.soundscape.device.SoundscapeItem
import io.reactivex.Completable

class AddAllSoundscapesUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Adds all saved soundscapes to the home view.
     **/
    fun execute(soundscapeItemList: List<SoundscapeItem>): Completable {
        return soundScape.addSounds(soundscapeItemList)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}