package com.example.dinhh.soundscape.data.local

import com.example.dinhh.soundscape.data.entity.LocalRecord
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeLocalData {

    fun saveLocalRecord(localRecord: LocalRecord): Completable

    fun getAllLocalRecords(): Single<List<LocalRecord>>
}

class SoundscapeLocalDataImpl(private val recordDao: RecordDao): SoundscapeLocalData {
    override fun saveLocalRecord(localRecord: LocalRecord): Completable {
        return Completable.fromAction {
            recordDao.insert(localRecord)
        }
    }

    override fun getAllLocalRecords(): Single<List<LocalRecord>> {
        return recordDao.getAll()
    }
}
