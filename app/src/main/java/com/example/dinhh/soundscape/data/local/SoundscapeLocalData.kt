package com.example.dinhh.soundscape.data.local

import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeLocalData {

    fun saveLocalSoundscapes(localSoundscape: LocalSoundscape): Completable

    fun getAllLocalSoundscapes(): Single<List<LocalSoundscape>>
}

class SoundscapeLocalDataImpl(private val localSoundscapeDao: SoundscapeDao): SoundscapeLocalData {
    override fun saveLocalSoundscapes(localSoundscape: LocalSoundscape): Completable {
        return Completable.fromAction {
            localSoundscapeDao.insert(localSoundscape)
        }
    }

    override fun getAllLocalSoundscapes(): Single<List<LocalSoundscape>> {
        return localSoundscapeDao.getAll()
    }
}
