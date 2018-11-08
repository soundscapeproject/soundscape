package com.example.dinhh.soundscape.data.repository

import com.example.dinhh.soundscape.data.database.SoundscapeLocalData
import com.example.dinhh.soundscape.data.entity.SoundScapeRecord
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeRepository {

    fun saveSoundscapeRecord(soundScapeRecord: SoundScapeRecord): Completable

    fun getAllSoundSscapeRecord(): Single<List<SoundScapeRecord>>
}

class SoundscapeRepositoryImpl(private val soundscapeLocalData: SoundscapeLocalData): SoundscapeRepository {

    override fun saveSoundscapeRecord(soundScapeRecord: SoundScapeRecord): Completable {
        return soundscapeLocalData.saveSoundscapeRecord(soundScapeRecord)
    }

    override fun getAllSoundSscapeRecord(): Single<List<SoundScapeRecord>> {
        return soundscapeLocalData.getAllSoundscapeRecords()
    }
}