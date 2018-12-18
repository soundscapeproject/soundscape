package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Completable

class DeleteSingleSoundScape(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {


    /**
    Deletes the selected sound from the soundscape.
     **/
    fun execute(localSoundscape: LocalSoundscape): Completable {

        return soundscapeRepository.deleteLocalSoundScape(localSoundscape)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}