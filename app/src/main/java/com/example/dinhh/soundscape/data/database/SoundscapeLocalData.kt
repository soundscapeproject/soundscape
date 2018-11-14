package com.example.dinhh.soundscape.data.database

import com.example.dinhh.soundscape.data.entity.SoundScapeRecord
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeLocalData {

    fun saveSoundscapeRecord(soundScapeRecord: SoundScapeRecord): Completable

    fun getAllSoundscapeRecords(): Single<List<SoundScapeRecord>>
}

class SoundscapeLocalDataImpl(private val soundscapeRecordDao: SoundscapeRecordDao): SoundscapeLocalData {

    override fun saveSoundscapeRecord(soundScapeRecord: SoundScapeRecord): Completable {
        return Completable.fromAction {
            soundscapeRecordDao.insert(soundScapeRecord)
        }
    }

    override fun getAllSoundscapeRecords(): Single<List<SoundScapeRecord>> {
        return soundscapeRecordDao.getAll()
    }
}