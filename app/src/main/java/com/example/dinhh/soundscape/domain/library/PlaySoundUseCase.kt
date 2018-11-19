package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.PlaySound
import io.reactivex.Completable

class PlaySoundUseCase (private val playSound: PlaySound, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedSound: String): Completable {
        return playSound.playSound(selectedSound)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}