package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.local.SoundscapeLocalData
import com.example.dinhh.soundscape.data.pref.SharedPref
import com.example.dinhh.soundscape.data.remote.SoundscapeRemoteData
import io.reactivex.Completable
import io.reactivex.Single

interface RecordRepository {

    fun saveRecord(localRecord: LocalRecord): Completable

    fun updateRecord(localRecord: LocalRecord): Completable

    fun getLocalRecords(): Single<List<LocalRecord>>

    fun deleteLocalRecord(id: Long): Completable

    fun uploadLocalRecord(localRecord: LocalRecord): Completable
}

class RecordRepositoryImpl(
    private val soundscapeRemoteData: SoundscapeRemoteData,
    private val soundscapeLocalData: SoundscapeLocalData,
    private val sharedPref: SharedPref
): RecordRepository {

    override fun updateRecord(localRecord: LocalRecord): Completable {
        return soundscapeLocalData.updateLocalRecord(localRecord)
    }

    override fun saveRecord(localRecord: LocalRecord): Completable {
        return soundscapeLocalData.saveLocalRecord(localRecord)
    }

    override fun getLocalRecords(): Single<List<LocalRecord>> {
        return soundscapeLocalData.getAllLocalRecords()
    }

    override fun deleteLocalRecord(id: Long): Completable {
        return soundscapeLocalData.deleteRecord(id)
    }

    override fun uploadLocalRecord(localRecord: LocalRecord): Completable {
        return Completable.complete()
//        return soundscapeRemoteData.uploadRecord(sharedPref.getToken().blockingGet(), localRecord)
    }
}