package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.*
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import io.reactivex.Single

@Dao
interface SoundscapeDao {

    @Insert
    fun insert(localSoundscape: LocalSoundscape)

    @Update
    fun update(localSoundscape: LocalSoundscape)

    @Query("SELECT * from ${DatabaseConfig.SOUNDSCAPE_TABLE_NAME}")
    fun getAll() : Single<List<LocalSoundscape>>

    @Query("SELECT * from ${DatabaseConfig.SOUNDSCAPE_TABLE_NAME} where sound_id = :id")
    fun getOne(id: Long) : Single<LocalSoundscape>

    @Delete
    fun delete(localSoundscape: LocalSoundscape)
}