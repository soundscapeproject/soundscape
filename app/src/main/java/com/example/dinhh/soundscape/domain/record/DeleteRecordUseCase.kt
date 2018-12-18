package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.repository.RecordRepository
import io.reactivex.Completable

class DeleteRecordUseCase(private val recordRepository: RecordRepository, private val schedulerProvider: SchedulerProvider) {

    /**
    Deletes the locally recorded sound.
     **/
    fun execute(id: Long): Completable {
        return recordRepository.deleteLocalRecord(id)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}