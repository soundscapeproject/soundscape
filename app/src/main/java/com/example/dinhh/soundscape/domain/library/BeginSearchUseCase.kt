package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.data.repository.SoundscapeRepository
import io.reactivex.Single

class BeginSearchUseCase (private val soundScapeRepository: SoundscapeRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedCategory: String): Single<List<List<Sound>>> {
        return soundScapeRepository.beginSearch(selectedCategory)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}