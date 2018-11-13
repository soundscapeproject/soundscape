package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.device.Record
import io.reactivex.Single

class StopRecordUseCase(private val record: Record, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Single<String> {
        return record.stopRecording()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}