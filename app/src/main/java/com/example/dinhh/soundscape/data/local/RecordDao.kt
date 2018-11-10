package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.dinhh.soundscape.data.entity.LocalRecord
import io.reactivex.Single

@Dao
interface RecordDao {

    @Insert
    fun insert(localRecord: LocalRecord)

    @Query("SELECT * from ${DatabaseConfig.RECORD_TABLE_NAME}")
    fun getAll() : Single<List<LocalRecord>>

    @Delete
    fun delete(localRecord: LocalRecord)
}