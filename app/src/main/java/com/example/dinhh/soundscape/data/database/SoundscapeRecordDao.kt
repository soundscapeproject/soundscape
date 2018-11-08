package com.example.dinhh.soundscape.data.database

import android.arch.persistence.room.*
import com.example.dinhh.soundscape.data.entity.SoundScapeRecord
import io.reactivex.Single

@Dao
interface SoundscapeRecordDao {

    @Insert
    fun insert(soundScapeRecord: SoundScapeRecord)

    @Query("SELECT * from ${DatabaseConfig.SOUNDSCAPE_TABLE_NAME}")
    fun getAll() : Single<List<SoundScapeRecord>>
}
