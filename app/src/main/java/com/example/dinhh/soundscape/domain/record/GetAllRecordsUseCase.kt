package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.SoundScapeRecord
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Single

class GetAllRecordsUseCase(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Single<List<SoundScapeRecord>> {
        return soundscapeRepository.getAllSoundSscapeRecord()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}