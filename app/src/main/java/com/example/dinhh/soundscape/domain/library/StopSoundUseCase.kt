package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Player
import io.reactivex.Completable

class StopSoundUseCase (private val player: Player, private val schedulerProvider: SchedulerProvider) {

    /**
    Stops mediaplayer from playing a sound in the library.
     **/
    fun execute(): Completable {
        return player.stopSound()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}