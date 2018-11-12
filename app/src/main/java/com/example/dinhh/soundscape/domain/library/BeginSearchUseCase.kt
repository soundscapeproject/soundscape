package com.example.dinhh.soundscape.domain.library

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Library
import io.reactivex.Completable

class BeginSearchUseCase (private val library: Library, private val schedulerProvider: SchedulerProvider) {

    fun execute(selectedCategory: String): Completable {
        return library.beginSearch(selectedCategory)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}