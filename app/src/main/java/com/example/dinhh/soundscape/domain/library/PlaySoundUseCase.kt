package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Player
import io.reactivex.Completable

class PlaySoundUseCase (private val player: Player, private val schedulerProvider: SchedulerProvider) {

    /**
    Plays the selected sound in the library.
     **/
    fun execute(selectedSound: String): Completable {
        return player.playSound(selectedSound, 1f) //Start with 100%
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}