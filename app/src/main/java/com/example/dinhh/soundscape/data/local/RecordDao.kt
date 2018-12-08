package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.*
import com.example.dinhh.soundscape.data.entity.LocalRecord
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RecordDao {

    @Insert
    fun insert(localRecord: LocalRecord)

    @Update
    fun update(localRecord: LocalRecord)

    @Query("SELECT * from ${DatabaseConfig.RECORD_TABLE_NAME} order by createdAt Desc")
    fun getAll() : Single<List<LocalRecord>>

    @Query("DELETE from ${DatabaseConfig.RECORD_TABLE_NAME} WHERE sound_id = :id")
    fun delete(id: Long)
}