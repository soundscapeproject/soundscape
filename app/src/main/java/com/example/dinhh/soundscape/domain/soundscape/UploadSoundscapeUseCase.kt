package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Completable

class UploadSoundscapeUseCase(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(localSoundscape: LocalSoundscape): Completable {

        localSoundscape.isUploaded = true

        return soundscapeRepository.uploadSoundScape(localSoundscape)
            .andThen(soundscapeRepository.updateLocalSoundScape(localSoundscape))
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}