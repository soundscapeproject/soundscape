package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Record
import io.reactivex.Completable

class StartRecordUseCase(private val record: Record, private val schedulerProvider: SchedulerProvider) {

    /**
    Starts recording to a temporary file.
     **/
    fun execute(): Completable {
        return record.startRecording()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}