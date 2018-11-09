package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.dinhh.soundscape.data.entity.LocalRecord
import io.reactivex.Single

interface RecordDao {

    @Insert
    fun insert(localRecord: LocalRecord)

    @Query("SELECT * from ${DatabaseConfig.SOUNDSCAPE_TABLE_NAME}")
    fun getAll() : Single<List<LocalRecord>>
}