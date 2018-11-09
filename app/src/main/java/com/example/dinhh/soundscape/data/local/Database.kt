package com.example.dinhh.soundscape.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dinhh.soundscape.data.entity.LocalRecord

@Database(entities = [LocalRecord::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}