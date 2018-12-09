package com.example.dinhh.soundscape.domain.soundscape

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Single

class GetOneLocalSoundscapeUseCase(private val soundscapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(id: Long): Single<LocalSoundscape> {

        return soundscapeRepository.getOneLocalSoundScape(id)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}