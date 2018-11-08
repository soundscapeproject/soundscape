package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.SoundScapeRecord
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Completable

class SaveRecordUseCase(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(soundScapeRecord: SoundScapeRecord): Completable {
        return soundscapeRepository.saveSoundscapeRecord(soundScapeRecord)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}