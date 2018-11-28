package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Player
import io.reactivex.Completable

class StopSoundUseCase (private val player: Player, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedPosition: Int): Completable {
        return player.stopSound(selectedPosition)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}