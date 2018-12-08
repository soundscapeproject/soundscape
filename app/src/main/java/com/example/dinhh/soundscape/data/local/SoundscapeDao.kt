package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SoundscapeDao {

    @Insert
    fun insert(localSoundscape: LocalSoundscape)

    @Query("SELECT * from ${DatabaseConfig.SOUNDSCAPE_TABLE_NAME}")
    fun getAll() : Single<List<LocalSoundscape>>

    @Delete
    fun delete(localSoundscape: LocalSoundscape)
}