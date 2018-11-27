package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Player
import io.reactivex.Completable

class PlaySoundUseCase (private val player: Player, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedSound: String, selectedPosition: Int): Completable {
        return player.playSound(selectedSound, selectedPosition)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}