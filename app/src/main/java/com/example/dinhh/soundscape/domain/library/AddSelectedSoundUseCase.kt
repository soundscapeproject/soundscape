package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.AddSound
import io.reactivex.Completable

class AddSelectedSoundUseCase (private val addSound: AddSound, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedSound: String, title: String, length: Int , category: String, volume: Int): Completable {
        return addSound.addSound(selectedSound, title, length, category, volume)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}