package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.SoundScape
import io.reactivex.Completable

class ChangeVolumeUseCase(private val soundScape: SoundScape, private val schedulerProvider: SchedulerProvider) {

    /**
    Changes the selected sound's volume level in the soundscape.
     **/
    fun execute(index: Int, progress: Int): Completable {
        return soundScape.changeVolume(index, progress)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}