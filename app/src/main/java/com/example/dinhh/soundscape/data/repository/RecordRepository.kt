package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.local.SoundscapeLocalData
import io.reactivex.Completable
import io.reactivex.Single

interface RecordRepository {

    fun saveRecord(localRecord: LocalRecord): Completable

    fun getLocalRecords(): Single<List<LocalRecord>>

    fun deleteLocalRecord(localRecord: LocalRecord): Completable
}

class RecordRepositoryImpl(private val recordLocalData: Record): RecordRepository {

    override fun saveRecord(localRecord: LocalRecord): Completable {
        return recordLocalData.save(localRecord)
    }

    override fun getLocalRecords(): Single<List<LocalRecord>> {
        return recordLocalData.getAllLocalRecords()
    }

    override fun deleteLocalRecord(localRecord: LocalRecord): Completable {
        return recordLocalData.deleteRecord(localRecord)
    }
}