package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.local.SoundscapeLocalData
import io.reactivex.Completable
import io.reactivex.Single

interface RecordRepository {

    fun saveRecord(localRecord: LocalRecord): Completable

    fun getLocalRecords(): Single<List<LocalRecord>>
}

class RecordRepositoryImpl(private val soundscapeLocalData: SoundscapeLocalData): RecordRepository {

    override fun saveRecord(localRecord: LocalRecord): Completable {
        return soundscapeLocalData.saveLocalRecord(localRecord)
    }

    override fun getLocalRecords(): Single<List<LocalRecord>> {
        return soundscapeLocalData.getAllLocalRecords()
    }
}