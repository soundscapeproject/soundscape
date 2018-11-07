package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Record
import io.reactivex.Completable

class PlayRecordUseCase(private val record: Record, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Completable {
        return record.playAudio()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}