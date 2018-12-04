package com.example.dinhh.soundscape.data.local

import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import io.reactivex.Completable
import io.reactivex.Single

interface SoundscapeLocalData {

    fun saveLocalRecord(localRecord: LocalRecord): Completable

    fun getAllLocalRecords(): Single<List<LocalRecord>>

    fun deleteRecord(localRecord: LocalRecord): Completable

    fun saveLocalSoundscapes(localSoundscape: LocalSoundscape): Completable

    fun getAllLocalSoundscapes(): Single<List<LocalSoundscape>>
}

class SoundscapeLocalDataImpl(
    private val soundscapeDao: SoundscapeDao,
    private val recordDao: RecordDao): SoundscapeLocalData {

    override fun saveLocalSoundscapes(localSoundscape: LocalSoundscape): Completable {
        return Completable.fromAction {
            soundscapeDao.insert(localSoundscape)
        }
    }

    override fun getAllLocalSoundscapes(): Single<List<LocalSoundscape>> {
        return soundscapeDao.getAll()
    }

    override fun saveLocalRecord(localRecord: LocalRecord): Completable {
        return Completable.fromAction {
            recordDao.insert(localRecord)
        }
    }

    override fun getAllLocalRecords(): Single<List<LocalRecord>> {
        return recordDao.getAll()
    }

    override fun deleteRecord(localRecord: LocalRecord): Completable {
        return Completable.fromAction {
            recordDao.delete(localRecord)
        }
    }


}
