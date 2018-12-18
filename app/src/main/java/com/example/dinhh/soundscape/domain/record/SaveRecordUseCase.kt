package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.repository.RecordRepository
import com.example.dinhh.soundscape.device.Record
import io.reactivex.Completable

class SaveRecordUseCase(
    private val recordRepository: RecordRepository,
    private val record: Record,
    private val schedulerProvider: SchedulerProvider
) {

    /**
    Saves the locally recorded sound to the local storage.
     **/
    fun execute(localRecord: LocalRecord): Completable {
        return recordRepository.saveRecord(localRecord)
            .andThen(record.resetTempFile())
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}