package com.example.dinhh.soundscape.domain.record

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.repository.RecordRepository
import io.reactivex.Single

class GetRecordsUseCase(private val recordRepository: RecordRepository, private val schedulerProvider: SchedulerProvider) {

    /**
    Gets all saved, locally recorded sounds.
     **/
    fun execute(): Single<List<LocalRecord>> {
        return recordRepository.getLocalRecords()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }
}