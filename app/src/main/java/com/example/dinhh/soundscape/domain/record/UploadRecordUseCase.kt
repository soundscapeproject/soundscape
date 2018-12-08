package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.repository.RecordRepository
import io.reactivex.Completable

class UploadRecordUseCase(
    private val recordRepository: RecordRepository,
    private val schedulerProvider: SchedulerProvider) {

    fun execute(localRecord: LocalRecord): Completable {

        localRecord.isUploaded = true

        return recordRepository.uploadLocalRecord(localRecord)
            .andThen(recordRepository.uploadLocalRecord(localRecord))
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}