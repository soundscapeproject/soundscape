package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Completable

class UpdateLocalSoundScapeUseCase(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    /**
    Updates the data in the locally saved soundscape, when user wants to modify an existing soundscape.
     **/
    fun execute(localSoundscape: LocalSoundscape): Completable {

        return soundscapeRepository.updateLocalSoundScape(localSoundscape)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}