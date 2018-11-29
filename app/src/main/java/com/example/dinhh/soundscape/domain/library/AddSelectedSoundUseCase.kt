package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.AddSound
import io.reactivex.Completable

class AddSelectedSoundUseCase (private val addSound: AddSound, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedSound: String, selectedPosition: Int, title: String, length: Int): Completable {
        return addSound.addSound(selectedSound, selectedPosition, title, length)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}